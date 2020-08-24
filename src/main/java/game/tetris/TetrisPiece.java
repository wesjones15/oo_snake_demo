package game.tetris;

import game.Segment;

import java.util.ArrayList;

public abstract class TetrisPiece {
    private ArrayList<Segment> segments;
//    private Integer anchorPointX; // relative to boardSize, not gamewindow
//    private Integer anchorPointY;
    private Segment anchorPoint;
    // anchor point exists to handle rotation. should be in same position for all blocks
    // i suggest upper-left-corner in 4x4grid around block

    // when any block in game.Segment[] getBottomMostBlocksInPiece contacts a block in game.Segment[] placedTetrisPieces
    // then stop tetrisBlockMotion, add each segment in currentTetrisBlock into placedTetrisPieces ArrayList
    // and generate new tetrisPiece of random type and start descending from top

    public Segment getAnchorLocation() {
        return this.anchorPoint;
    }

    public void descend() {
        this.anchorPoint.setPosY(anchorPoint.getPosY()-1);
        for (Segment segment : segments) {
            // recalculate segment position relative to new anchor point
        }
    }

    public abstract void rotateClockwise();

    public boolean checkCollisionWithPlacedBlocks(ArrayList<Segment> placedPieces) {
        // call this method before every move
        // only execute move if return true and no collision
        return true;
    }

    // getBottommostBlocks
    // for each block in piece, check if its position is within 1 of a placed block maybe
    public ArrayList<Segment> getBottommostSegments() {
        // check for lowest blocks on tetrisPiece
        // lowest y piece for each horiz unit size
        return new ArrayList<Segment>();
    }
}
