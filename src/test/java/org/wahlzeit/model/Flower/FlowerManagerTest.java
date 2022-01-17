package org.wahlzeit.model.Flower;

import org.junit.Test;
import org.wahlzeit.model.CartesianCoordinate;
import org.wahlzeit.model.Coordinate;
import org.wahlzeit.model.Location;
import org.wahlzeit.model.flower.Flower;
import org.wahlzeit.model.flower.FlowerManager;
import org.wahlzeit.model.flower.FlowerPhoto;
import org.wahlzeit.model.flower.FlowerType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FlowerManagerTest {
    @Test
    public void testCreateFlower(){
        FlowerManager fm = new FlowerManager();
        Flower flower = fm.createFlower("Rose");
        assertTrue(flower.getType() == fm.getFlowerType("Rose"));

        Flower flower2 = fm.createFlower("Rose");
        assertEquals(fm.flowertypes.size(), 1);

        try{
            fm.createFlower(null);
        }catch (IllegalArgumentException e){

        }

        try{
            fm.createFlower("");
        }catch (IllegalArgumentException e){

        }
    }
}
