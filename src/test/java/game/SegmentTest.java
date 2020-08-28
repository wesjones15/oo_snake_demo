package game;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

public class SegmentTest {

    @Test
    public void testGetterSetter() {
        Segment segment = new Segment(0,0);
        segment.setPosY(5);
        segment.setPosX(3);
        Assert.assertEquals(3, segment.getPosX());
        Assert.assertEquals(5, segment.getPosY());
    }

    @Test
    public void testGetOccupied() {
        Segment seg1 = new Segment(4,5);
        Segment seg2 = new Segment(4,5);
        Assert.assertTrue(seg1.getOccupied(seg2));
    }

    @Test
    public void testGetOccupied2() {
        Segment seg1 = new Segment(2,5);
        Segment seg2 = new Segment(4,5);
        Assert.assertFalse(seg1.getOccupied(seg2));
    }
}
