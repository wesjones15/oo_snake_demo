package game.tetris;

import game.Segment;

import java.awt.*;
import java.util.ArrayList;

public abstract class TetrisPiece {
    private ArrayList<TetrisSegment> segments;
    // relative to boardSize, not gamewindow
    private Segment anchorPoint;
    // anchor point exists to handle rotation. should be in same position for all blocks
    // i suggest upper-left-corner in 4x4grid around block

    private ArrayList<Integer[]> states;
    private Integer state;

    public ArrayList<Integer[]> getStates() {
        return states;
    }

    public void setStates(ArrayList<Integer[]> states) {
        this.states = states;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public abstract Color getColor();


// when any block in game.Segment[] getBottomMostBlocksInPiece contacts a block in game.Segment[] placedTetrisPieces
    // then stop tetrisBlockMotion, add each segment in currentTetrisBlock into placedTetrisPieces ArrayList
    // and generate new tetrisPiece of random type and start descending from top

    public ArrayList<TetrisSegment> getSegments() {
        return segments;
    }

    public void setSegments(ArrayList<TetrisSegment> segments) {
        this.segments = segments;
    }

    public void updateSegmentsRelativeToAnchorPoint() {
        ArrayList<TetrisSegment> newSegments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int x = states.get(state)[i*2];
            int y = states.get(state)[(i*2)+1];
            newSegments.add(new TetrisSegment(this.anchorPoint.getPosX()+x, this.anchorPoint.getPosY()+y, this.getColor()));
        }
        this.setSegments(newSegments);
    }

    public ArrayList<TetrisSegment> getNextPosition(int action) {
        int newState = this.state;
        int anchorAdjust = 0;
        if (action < 4) {
            if (states.size() < 4 && action > 2) {
                action = action - 2;
            }
            newState = action;
        } else if (action == 4) {
            anchorAdjust = 1;
        }
        //action = 0-3, check i
        ArrayList<TetrisSegment> newSegments = new ArrayList<>();
        for (int i = 0; i < this.getSegments().size(); i++) {
            int x = states.get(newState)[i*2];
            int y = states.get(newState)[(i*2)+1];
            newSegments.add(new TetrisSegment(this.anchorPoint.getPosX()+x, this.anchorPoint.getPosY()-anchorAdjust+y, this.getColor()));
        }
        return newSegments;
    }

    public Segment getAnchorPoint() {
        return anchorPoint;
    }

    public void setAnchorPoint(Segment anchorPoint) {
        this.anchorPoint = anchorPoint;
    }

    public void descend() {
        updateAnchorPosition(0,1);
        updateSegmentsRelativeToAnchorPoint();
    }

    public void rotateClockwise() {
        state += 1;
        if (state >= states.size()) {
            state = 0;
        }
        updateSegmentsRelativeToAnchorPoint();
    }

    public void rotateCounterClockwise() {
        state -= 1;
        if (state < 0) {
            state = states.size()-1;
        }
        updateSegmentsRelativeToAnchorPoint();
    }

//    public boolean checkCollisionWithPlacedBlocks(ArrayList<Segment> placedPieces) {
//        // call this method before every move
//        // only execute move if return true and no collision
//        return true;
//    }

    // getBottommostBlocks
    // for each block in piece, check if its position is within 1 of a placed block maybe
    public ArrayList<TetrisSegment> getBottommostSegments() {

        return getSegments();
    }

    public Segment getLowestSegment() {
        Segment lowest = segments.get(0);
        for (Segment segment: segments) {
            if (segment.getPosY() > lowest.getPosY()) {
                lowest = segment;
            }
        }
        return lowest;
    }

    public void updateVerticalAnchorPosition(int amount) {
        this.anchorPoint.setPosY(this.anchorPoint.getPosY()+amount);
    }

    public void updateAnchorPosition(int x, int y) {
        this.anchorPoint.setPosX(this.anchorPoint.getPosX()+x);
        this.anchorPoint.setPosY(this.anchorPoint.getPosY()+y);
    }

}
