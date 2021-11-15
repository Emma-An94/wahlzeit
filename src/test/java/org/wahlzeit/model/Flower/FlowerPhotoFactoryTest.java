package org.wahlzeit.model.Flower;

import org.junit.Test;
import org.wahlzeit.model.Coordinate;
import org.wahlzeit.model.Location;
import org.wahlzeit.model.flower.FlowerPhoto;
import org.wahlzeit.model.flower.FlowerPhotoFactory;

import static org.junit.Assert.assertTrue;

public class FlowerPhotoFactoryTest {
    @Test
    public void testFlowerPhotoFactoryInstance() {
        FlowerPhotoFactory instance = FlowerPhotoFactory.getInstance();
        FlowerPhoto photo = new FlowerPhoto();
        Location location = photo.getLocation();
        FlowerPhoto photo1 = instance.createPhoto();
        Location location1 = photo1.getLocation();
        assertTrue(location.getCoordinate().isEqual(location.getCoordinate()));
    }
}
