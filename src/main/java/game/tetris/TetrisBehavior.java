package game.tetris;

import game.Segment;

import java.util.ArrayList;

public class TetrisBehavior {
    private ArrayList<Segment> placedPieces;
    private boolean gameOver = false;

    public TetrisBehavior() {
        this.placedPieces = new ArrayList<Segment>();
    }

    public Boolean checkCollisionWithPiece(TetrisPiece tetrisPiece) {
        boolean collide = false;
        for (Segment segment : placedPieces) {
            for (Segment piece: tetrisPiece.getBottommostSegments()) {
                if (piece.getOccupied(segment)) {
                    collide = true;
                    break;
                }
            }
            if (collide) break;
        }
        return collide;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
