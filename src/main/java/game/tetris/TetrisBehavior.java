package game.tetris;

import game.Segment;
import game.tetris.piece.*;

import java.util.ArrayList;
import java.util.Random;

public class TetrisBehavior {
    private TetrisPiece currentTetrisPiece;

    private ArrayList<TetrisPiece> nextThreePieces;
    private TetrisPiece heldTetrisPiece;
    private ArrayList<TetrisSegment> placedPieces;
    private boolean gameOver = false;
    private int boardSizeX = 10;
    private int boardSizeY = 20;
    private int score = 0;

    public TetrisBehavior() {
        this.currentTetrisPiece = this.getNewTetrisPiece();
        this.placedPieces = new ArrayList<TetrisSegment>();
        initNextThreePieces();
    }

    public void initNextThreePieces() {
        this.nextThreePieces = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            nextThreePieces.add(getNewTetrisPiece());
        }
    }

    // Getters & Setters
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

    public void increaseScoreByAmount(int amount) {
        this.score = this.score + amount;
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

    public ArrayList<TetrisPiece> getNextThreePieces() {
        return nextThreePieces;
    }

    public TetrisPiece getHeldTetrisPiece() {
        return heldTetrisPiece;
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
        this.placedPieces.addAll(currentTetrisPiece.getSegments());
    }


    // Collision Detection
//TODO: fix getting locked on pieces above or beside
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

    public Boolean checkCollisionWithSegment(Segment segment) {
        boolean collide = false;
        for (Segment piece : placedPieces) {
            if (segment.getOccupied(piece)) {
                collide = true;
                break;
            }
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

    //TODO: refactor so that it takes into account placedPieces
    //TODO: currently if you move sideways at bottom, it will jump to placedblocks above
    // this is only an issue with horizontal movement, so maybe separate make special case
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
                break;
            } else if (segment.getPosY() >= getHighestPlacedPieceInColumns(currentTetrisPiece.getColumns())
            && checkCollisionWithSegment(segment)) {
                currentTetrisPiece.updateAnchorPosition(0,(getHighestPlacedPieceInColumns(currentTetrisPiece.getColumns())-segment.getPosY()-1));
                break;
            }
        }
        currentTetrisPiece.updateSegmentsRelativeToAnchorPoint();
//        return tetrisPiece;
    }


    // Generate New Tetris Piece
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

    public void getNextTetrisPiece() {
        currentTetrisPiece = nextThreePieces.get(0);
        nextThreePieces.remove(0);
        nextThreePieces.add(getNewTetrisPiece());
    }

    // Row Clearing Logic
    //TODO: call removeClearedRow multiple times
    public void checkForRowClear() {
        boolean clearedRowPrev;
        int points = 0;
        do {
            clearedRowPrev = false;
            for (int i = boardSizeY - 1; i >= 0; i--) {
                int row_ct = 0;
                for (int j = 0; j < boardSizeX; j++) {
                    Segment location = new Segment(j, i);
                    for (Segment piece : placedPieces) {
                        if (location.getOccupied(piece)) {
                            row_ct += 1;
                        }
                    }
                }
                if (row_ct >= boardSizeX) {
                    removeClearedRow(i);
                    points = points == 0 ? 1 : points * 2;
                    clearedRowPrev = true;
                    // add points to score
                    row_ct = 0;
                }
            }
        } while (clearedRowPrev);
        increaseScoreByAmount(points);
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
    }

    // Update Position of Tetris Piece
    public void moveTetrisPiece(int x, int y) {
        currentTetrisPiece.updateAnchorPosition(x,y);
        currentTetrisPiece.updateSegmentsRelativeToAnchorPoint();
        // check if tetris piece is past boundary and move to within bounds
        isTetrisPiecePastBoundary();
    }

    // Main Game Loop
    public void run() {
        if (!isGameOver()) {

            // Add game logic that occurs each tick

            // maybe make this occur at different rate than tick to allow more frequent movement
//            currentTetrisPiece.descend();

            // Check for collision with bottom of screen
            // Check for collision with placed pieces
            if (checkCollisionWithScreenBottom(currentTetrisPiece) || checkCollisionWithPiece(currentTetrisPiece)){
                addPlacedPieces();
                getNextTetrisPiece();
                checkForRowClear();
            } else {
                currentTetrisPiece.descend();
            }

            // Check game over condition
            checkForGameOver();
        }
    }

    public void checkForGameOver() {
        for (Segment segment: placedPieces) {
            if (segment.getPosY()<2) {
                this.setGameOver(true);
                break;
            }
        }
    }

    // Execution of User Input
    //TODO: for actionUP and actionDOWN, add additional check for collision
    //i think this is a fix
    public void actionUP() {
        int dist = getHighestPlacedPieceInColumns(currentTetrisPiece.getColumns())
                - currentTetrisPiece.getAnchorPoint().getPosY() - 4;
        moveTetrisPiece(0,dist);
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

    public void actionHold() {
        if (null == heldTetrisPiece) {
            heldTetrisPiece = currentTetrisPiece;
            TetrisSegment newAnchor = new TetrisSegment(boardSizeX/2-2,-4, currentTetrisPiece.getColor());
            heldTetrisPiece.setAnchorPoint(newAnchor);
            heldTetrisPiece.updateSegmentsRelativeToAnchorPoint();
            getNextTetrisPiece();
        } else {
            TetrisPiece temp = heldTetrisPiece;
            heldTetrisPiece = currentTetrisPiece;
            TetrisSegment newAnchor = new TetrisSegment(boardSizeX/2-2,-4, currentTetrisPiece.getColor());
            heldTetrisPiece.setAnchorPoint(newAnchor);
            heldTetrisPiece.updateSegmentsRelativeToAnchorPoint();
            currentTetrisPiece = temp;
        }
    }

    //todo get landingspot currently updates currenttetrispiece when logically it shouldnt
    public TetrisPiece getLandingSpot() {
        try {
            Class<? extends TetrisPiece> shape = currentTetrisPiece.getClass();
            TetrisPiece landedPiece = shape.newInstance();
            landedPiece.setState(currentTetrisPiece.getState());
            landedPiece.setAnchorPoint(new Segment(currentTetrisPiece.getAnchorPoint().getPosX(),currentTetrisPiece.getAnchorPoint().getPosY()));
            landedPiece.updateSegmentsRelativeToAnchorPoint();
            int dist = getHighestPlacedPieceInColumns(landedPiece.getColumns())
                    - landedPiece.getAnchorPoint().getPosY() - 4 ;
            landedPiece.updateAnchorPosition(0,dist);
            landedPiece.updateSegmentsRelativeToAnchorPoint();
            return landedPiece;
        } catch (Exception e) {
            return null;
        }
    }
}
