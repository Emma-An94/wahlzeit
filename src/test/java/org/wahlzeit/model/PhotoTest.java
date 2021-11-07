package org.wahlzeit.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class PhotoTest {
    @Test
    public void testPhotoWithLocationAndCoordinate() {
        Coordinate coordinate = new Coordinate(0,0,0);
        Photo photo = new Photo();
        Location location = photo.getLocation();
        assertTrue(coordinate.isEqual(location.getCoordinate()));
    }
}
