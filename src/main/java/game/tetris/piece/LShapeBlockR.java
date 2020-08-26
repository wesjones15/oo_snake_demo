package game.tetris.piece;

import game.Segment;
import game.tetris.TetrisPiece;

import java.awt.*;
import java.util.ArrayList;

public class LShapeBlockR extends TetrisPiece {
    private static final Integer[] state0 = {1,1,1,2,1,3,2,3};
    private static final Integer[] state1 = {1,2,2,2,3,2,3,1};
    private static final Integer[] state2 = {1,0,2,0,2,1,2,2};
    private static final Integer[] state3 = {2,1,1,1,0,1,0,2};
    private final Color color = Color.BLUE;
//    private ArrayList<Integer[]> states;
//    private Integer state;

    public LShapeBlockR(int posX, int posY) {
        this.setAnchorPoint(new Segment(posX,posY));
        this.setState(0);
        this.initPiece();
    }

    public LShapeBlockR() {
        this.setAnchorPoint(new Segment(0,0));
        this.setState(0);
        this.initPiece();
    }

    public Color getColor() {
        return color;
    }

    private void initPiece() {
        ArrayList<Integer[]> states = new ArrayList<>();
        states.add(state0);
        states.add(state1);
        states.add(state2);
        states.add(state3);
        this.setStates(states);
        updateSegmentsRelativeToAnchorPoint();
    }


}
