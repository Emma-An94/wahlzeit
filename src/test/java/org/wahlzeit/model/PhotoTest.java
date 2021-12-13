package org.wahlzeit.model;

import org.junit.Test;
import org.wahlzeit.model.flower.FlowerPhoto;

import java.sql.ResultSet;

import static org.junit.Assert.*;

public class PhotoTest {
    @Test
    public void testPhotoWithLocationAndCoordinate() {
        Coordinate coordinate = new CartesianCoordinate(0,0,0);
        Photo photo = new Photo();
        Location location = photo.getLocation();
        assertTrue(coordinate.isEqual(location.getCoordinate()));
    }

    @Test
    public void testPhotoInitialization(){
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
