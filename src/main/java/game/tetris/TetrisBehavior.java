package game.tetris;

import game.Segment;
import game.tetris.piece.*;

import java.util.ArrayList;
import java.util.Random;

public class TetrisBehavior {
//    private TetrisPiece currentTetrisPiece;

    private ArrayList<TetrisSegment> placedPieces;
    private boolean gameOver = false;
    private int boardSizeX = 8;
    private int boardSizeY = 16;

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


    public TetrisBehavior() {
        this.placedPieces = new ArrayList<TetrisSegment>();
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

    public void addPlacedPieces(TetrisPiece tetrisPiece) {
        for (TetrisSegment segment : tetrisPiece.getSegments()) {
            this.placedPieces.add(segment);
        }
    }

    public TetrisPiece getNewTetrisPiece() {
        int x = 4;
        int y = 0;
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
    }

    public TetrisPiece moveTetrisPiece(TetrisPiece tetrisPiece, int x, int y) {
        tetrisPiece.updateAnchorPosition(x,y);
        tetrisPiece.updateSegmentsRelativeToAnchorPoint();
        tetrisPiece = isTetrisPiecePastBoundary(tetrisPiece);
        return tetrisPiece;
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

    public TetrisPiece isTetrisPiecePastBoundary(TetrisPiece tetrisPiece) {
        for (Segment segment : tetrisPiece.getSegments()) {
            if (segment.getPosX() > boardSizeX-1) {
                tetrisPiece.updateAnchorPosition(boardSizeX-1-segment.getPosX(),0);
                break;
            } else if (segment.getPosX() < 0) {
                tetrisPiece.updateAnchorPosition(0-segment.getPosX(),0);
                break;
            }
            if (segment.getPosY() > boardSizeY) {
                tetrisPiece.updateAnchorPosition(0,-1);
            }
        }
        tetrisPiece.updateSegmentsRelativeToAnchorPoint();
        return tetrisPiece;
    }

    public void checkForGameOver() {
        for (Segment segment: placedPieces) {
            if (segment.getPosY()<2) {
                this.setGameOver(true);
                break;
            }
        }
    }
}
