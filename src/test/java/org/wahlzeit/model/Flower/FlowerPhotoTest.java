package org.wahlzeit.model.Flower;

import org.junit.Test;
import org.wahlzeit.model.Coordinate;
import org.wahlzeit.model.CartesianCoordinate;
import org.wahlzeit.model.Location;
import org.wahlzeit.model.Photo;
import org.wahlzeit.model.flower.FlowerPhoto;

import static org.junit.Assert.assertTrue;

public class FlowerPhotoTest {
    @Test
    public void testFlowerPhotoWithLocationAndCoordinate() {
        Coordinate coordinate = new CartesianCoordinate(0,0,0);
        FlowerPhoto photo = new FlowerPhoto();
        Location location = photo.getLocation();
        assertTrue(coordinate.equals(location.getCoordinate()));
    }
}
