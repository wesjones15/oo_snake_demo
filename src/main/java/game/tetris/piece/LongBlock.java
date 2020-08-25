package game.tetris.piece;

import game.Segment;
import game.tetris.TetrisPiece;

import java.awt.*;
import java.util.ArrayList;

public class LongBlock extends TetrisPiece {
    private static final Integer[] state0 = {1,0,1,1,1,2,1,3};
    private static final Integer[] state1 = {0,1,1,1,2,1,3,1};
    private final Color color = Color.CYAN;

    public LongBlock(int posX, int posY) {
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
