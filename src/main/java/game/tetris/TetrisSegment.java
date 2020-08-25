package game.tetris;

import game.Segment;

import java.awt.*;

public class TetrisSegment extends Segment {
    private Color color;

    public TetrisSegment(int posX, int posY, Color color) {
        super(posX, posY);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // otherwise, same as segemnt
    // i'll implement this later
    // color is a nice-to-have
}
