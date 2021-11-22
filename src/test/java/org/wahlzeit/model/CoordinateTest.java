package org.wahlzeit.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class CoordinateTest {

    private Coordinate cartesianCoordinate1 = new CartesianCoordinate(0.0,0.0,0.0);
    private CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(3.0, 4.0, 5.0);
    private Coordinate sphericCoordinate1 = new SphericCoordinate(1.0, 1.0, 1.0);
    private SphericCoordinate sphericCoordinate = new SphericCoordinate(2.0, 2.0, 2.0);

    @Test
    public void testGetCartesianDistance(){
        Coordinate coordinate1 = new CartesianCoordinate(2,4,4);
        assertEquals(6, cartesianCoordinate1.getCartesianDistance(coordinate1),0.0001);
    }

    @Test
    public void testIsEqual(){
        Coordinate coordinate1 = new CartesianCoordinate(0,0,0);
        assertTrue(cartesianCoordinate1.isEqual(coordinate1));
        assertTrue(cartesianCoordinate1.equals(coordinate1));

        Coordinate coordinate2 = new CartesianCoordinate(2,2,2);
        assertFalse(cartesianCoordinate1.isEqual(coordinate2));
        assertFalse(coordinate2.equals(2));

        assertFalse(sphericCoordinate.isEqual(sphericCoordinate1));
        assertTrue(sphericCoordinate.equals(sphericCoordinate));
        assertFalse(sphericCoordinate.equals(sphericCoordinate1));
        assertTrue(sphericCoordinate.getClass() == coordinate1.asSphericCoordinate().getClass());
    }
}
