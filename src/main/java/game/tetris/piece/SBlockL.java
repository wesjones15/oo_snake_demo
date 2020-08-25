package game.tetris.piece;

import game.Segment;
import game.tetris.TetrisPiece;

import java.awt.*;
import java.util.ArrayList;

public class SBlockL extends TetrisPiece {
    private static final Integer[] state0 = {1,2,2,2,2,1,3,1};
    private static final Integer[] state1 = {1,1,1,2,2,2,2,3};
    private final Color color = Color.RED;

    public SBlockL(int posX, int posY) {
        this.setAnchorPoint(new Segment(posX,posY));
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
        this.setStates(states);
        updateSegmentsRelativeToAnchorPoint();
    }
}
