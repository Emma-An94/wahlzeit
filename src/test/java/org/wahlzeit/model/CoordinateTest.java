package org.wahlzeit.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class CoordinateTest {

    private Coordinate coordinate = new Coordinate(0.0,0.0,0.0);

    @Test
    public void testGetDistance(){
        Coordinate coordinate1 = new Coordinate(2,4,4);
        double dis = coordinate.getDistance(coordinate1);
        assertEquals(6, coordinate.getDistance(coordinate1),0);
    }

    @Test
    public void testIsEqual(){
        Coordinate coordinate1 = new Coordinate(0,0,0);
        assertTrue(coordinate.isEqual(coordinate1));
        assertTrue(coordinate.equals(coordinate1));

        Coordinate coordinate2 = new Coordinate(2,2,2);
        assertFalse(coordinate.isEqual(coordinate2));
        assertFalse(coordinate2.equals(2));
    }
}
