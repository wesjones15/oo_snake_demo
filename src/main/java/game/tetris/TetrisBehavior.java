package game.tetris;

import game.Segment;
import game.tetris.piece.*;

import java.util.ArrayList;
import java.util.Random;

public class TetrisBehavior {
    private TetrisPiece currentTetrisPiece;

    private ArrayList<TetrisSegment> placedPieces;
    private boolean gameOver = false;
    private int boardSizeX = 8;
    private int boardSizeY = 16;
    private int score = 0;

    public TetrisBehavior() {
        this.currentTetrisPiece = this.getNewTetrisPiece();
        this.placedPieces = new ArrayList<TetrisSegment>();
    }

    public TetrisPiece getCurrentTetrisPiece() {
        return currentTetrisPiece;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increaseScore() {
        this.score +=1;
    }

    public int getBoardSizeX() {
        return boardSizeX;
    }

    public void setBoardSizeX(int boardSizeX) {
        this.boardSizeX = boardSizeX;
    }

    public int getBoardSizeY() {
        return boardSizeY;
    }

    public void setBoardSizeY(int boardSizeY) {
        this.boardSizeY = boardSizeY;
    }

    public Boolean checkCollisionWithPiece(TetrisPiece tetrisPiece) {
        boolean collide = false;
        for (Segment segment : placedPieces) {
            for (Segment piece: tetrisPiece.getBottommostSegments()) {
                if (piece.isWithinOneOf(segment)) {
                    collide = true;
                    break;
                }
            }
            if (collide) break;
        }
        return collide;
    }

    public Boolean checkCollisionWithScreenBottom(TetrisPiece tetrisPiece) {
        boolean onBottom = false;
        for (Segment segment : tetrisPiece.getBottommostSegments()) {
            if (segment.getPosY() >= boardSizeY-1) {
                onBottom = true;
                break;
            }
        }
        return onBottom;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public ArrayList<TetrisSegment> getPlacedPieces() {
        return placedPieces;
    }

    public void addPlacedPieces() {
        for (TetrisSegment segment : currentTetrisPiece.getSegments()) {
            this.placedPieces.add(segment);
        }
    }

    public TetrisPiece getNewTetrisPiece() {
        int x = boardSizeX/2-2;
        int y = -4;
        Random random = new Random();
        int num = random.nextInt(7);
        if (num==0) {
            return new LShapeBlockR(x,y);
        } else if (num==1) {
            return new LShapeBlockL(x,y);
        } else if (num==2) {
            return new LongBlock(x,y);
        } else if (num==3) {
            return new SBlockL(x,y);
        } else if (num==4) {
            return new SBlockR(x,y);
        } else if (num==5) {
            return new SquareBlock(x,y);
        } else {
            return new TBlock(x,y);
        }
    }

    public void checkForRowClear() {
        for (int i = boardSizeY-1; i >= 0; i--) {
            int row_ct = 0;
            for (int j = 0; j < boardSizeX; j++) {
                Segment location = new Segment(j,i);
                for (Segment piece : placedPieces) {
                    if (location.getOccupied(piece)){
                        row_ct += 1;
                    }
                }
            }
            if (row_ct >= boardSizeX) {
                removeClearedRow(i);
                // add points to score
                row_ct = 0;
            }
        }
    }

    // TODO: refactor
    public void removeClearedRow(int y_row) {
        ArrayList<TetrisSegment> newPlacedPieces = new ArrayList<>();
        for (int i = 0; i < placedPieces.size(); i++) {
            if (placedPieces.get(i).getPosY() < y_row) {
                TetrisSegment placedPiece = new TetrisSegment(placedPieces.get(i).getPosX(), placedPieces.get(i).getPosY()+1, placedPieces.get(i).getColor());
                newPlacedPieces.add(placedPiece);
            } else if (placedPieces.get(i).getPosY() != y_row) {
                newPlacedPieces.add(placedPieces.get(i));
            }
        }
        placedPieces = newPlacedPieces;
        increaseScore();
    }

    public void moveTetrisPiece(int x, int y) {
        currentTetrisPiece.updateAnchorPosition(x,y);
        currentTetrisPiece.updateSegmentsRelativeToAnchorPoint();
        // check if tetris piece is past boundary and move to within bounds
        isTetrisPiecePastBoundary();
//        return tetrisPiece;
    }

    public Integer getHighestPlacedPieceInColumn(Integer column) {
        int highest = boardSizeY;
        for (Segment segment : placedPieces) {
            if (segment.getPosX() == column) {
                if (highest > segment.getPosY())
                    highest = segment.getPosY();
            }
        }
        return highest;
    }

    public Integer getHighestPlacedPieceInColumns(ArrayList<Integer> columns) {
        int highest = boardSizeY;
        for (Integer column : columns) {
            for (Segment segment : placedPieces) {
                if (segment.getPosX() == column) {
                    if (highest > segment.getPosY())
                        highest = segment.getPosY();
                }
            }
        }
        return highest;
    }

    public void isTetrisPiecePastBoundary() {
        for (Segment segment : currentTetrisPiece.getSegments()) {
            if (segment.getPosX() > boardSizeX-1) {
                currentTetrisPiece.updateAnchorPosition(boardSizeX-1-segment.getPosX(),0);
                break;
            } else if (segment.getPosX() < 0) {
                currentTetrisPiece.updateAnchorPosition(0-segment.getPosX(),0);
                break;
            }
            if (segment.getPosY() > boardSizeY) {
                currentTetrisPiece.updateAnchorPosition(0,-1);
            }
        }
        currentTetrisPiece.updateSegmentsRelativeToAnchorPoint();
//        return tetrisPiece;
    }

    public void checkForGameOver() {
        for (Segment segment: placedPieces) {
            if (segment.getPosY()<2) {
                this.setGameOver(true);
                break;
            }
        }
    }

    public void run() {
        if (!isGameOver()) {

            // Add game logic that occurs each tick

            // maybe make this occur at different rate than tick to allow more frequent movement
            currentTetrisPiece.descend();

            // Check for collision with bottom of screen
            if (checkCollisionWithScreenBottom(currentTetrisPiece)){
                addPlacedPieces();
                currentTetrisPiece = getNewTetrisPiece();
                checkForRowClear();
            }

            // Check for collision with placed pieces
            if (checkCollisionWithPiece(currentTetrisPiece)) {
                addPlacedPieces();
                currentTetrisPiece = getNewTetrisPiece();
                checkForRowClear();
            }

            // Check game over condition
            checkForGameOver();
        }
    }

    public void actionUP() {
        int dist = getHighestPlacedPieceInColumns(currentTetrisPiece.getColumns())
                - currentTetrisPiece.getAnchorPoint().getPosY() - 5;
        currentTetrisPiece.updateAnchorPosition(0,dist);
        currentTetrisPiece.updateSegmentsRelativeToAnchorPoint();
    }

    public void actionDOWN() {
        moveTetrisPiece(0, 1);
    }

    public void actionLEFT() {
        moveTetrisPiece(-1, 0);
    }

    public void actionRIGHT() {
        moveTetrisPiece(1, 0);
    }

    public void actionRotateCCW() {
        currentTetrisPiece.rotateCounterClockwise();
    }

    public void actionRotateCW() {
        currentTetrisPiece.rotateClockwise();
    }


}
