package game.tetris.piece;

import game.Segment;
import game.tetris.TetrisPiece;

import java.awt.*;
import java.util.ArrayList;

public class SBlockR extends TetrisPiece {
    private static final Integer[] state0 = {1,1,2,1,2,2,3,2};
    private static final Integer[] state1 = {2,1,2,2,1,2,1,3};
    private final Color color = Color.GREEN;

    public SBlockR(int posX, int posY) {
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
