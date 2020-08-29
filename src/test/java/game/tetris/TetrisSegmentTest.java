package game.tetris;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class TetrisSegmentTest {
    @Test
    public void testGetSet() {
        TetrisSegment tetrisSegment = new TetrisSegment(1,2, Color.BLUE);
        tetrisSegment.setPosX(6);
        tetrisSegment.setPosY(3);
        tetrisSegment.setColor(Color.GREEN);
        Assert.assertEquals(6, tetrisSegment.getPosX());
        Assert.assertEquals(3, tetrisSegment.getPosY());
        Assert.assertEquals(Color.GREEN, tetrisSegment.getColor());
    }
}
