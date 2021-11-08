package org.wahlzeit.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class LocationTest {
    @Test
    public void testLocationWithCoordinate(){
        Location location = new Location();
        Coordinate coordinate = location.getCoordinate();
        Coordinate coordinate1 = new Coordinate(0.0,0.0,0.0);
        assertTrue(coordinate.isEqual(coordinate1));
    }
}
