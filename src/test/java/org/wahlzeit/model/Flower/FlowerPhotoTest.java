package org.wahlzeit.model.Flower;

import org.junit.Test;
import org.wahlzeit.model.*;
import org.wahlzeit.model.flower.FlowerPhoto;

import java.sql.ResultSet;

import static org.junit.Assert.assertTrue;

public class FlowerPhotoTest {
    @Test
    public void testFlowerPhotoWithLocationAndCoordinate() {
        Coordinate coordinate = new CartesianCoordinate(0,0,0);
        FlowerPhoto photo = new FlowerPhoto();
        Location location = photo.getLocation();
        assertTrue(coordinate.equals(location.getCoordinate()));
    }

    @Test
    public void testFlowerPhotoInitialization(){
        PhotoId id = null;
        try{
            FlowerPhoto photo = new FlowerPhoto(id);
        }catch (IllegalArgumentException e){
            // do nothing
        }

        Location location = null;
        try{
            FlowerPhoto photo = new FlowerPhoto(id);
        }catch (IllegalArgumentException e){
            // do nothing
        }

        ResultSet rset = null;
        try{
            FlowerPhoto photo = new FlowerPhoto(id);
        }catch (IllegalArgumentException e){
            // do nothing
        }
    }
}
