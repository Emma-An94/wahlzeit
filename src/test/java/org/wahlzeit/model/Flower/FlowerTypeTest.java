package org.wahlzeit.model.Flower;

import org.junit.Test;
import org.wahlzeit.model.flower.Flower;
import org.wahlzeit.model.flower.FlowerType;

import static org.junit.Assert.*;

public class FlowerTypeTest {
    @Test
    public void testHasInstance() {
        FlowerType ft = new FlowerType("Rose");
        FlowerType ft2 = new FlowerType("small Rose");
        ft.addSubType(ft2);
        Flower flower = ft.createInstance();
        assertTrue(ft.hasInstance(flower));
        assertFalse(ft2.hasInstance(flower));

        try{
            ft.hasInstance(null);
        }catch (IllegalArgumentException e){

        }
    }

    @Test
    public void testHasInstance2() {
        FlowerType ft = new FlowerType("Rose");
        FlowerType ft2 = new FlowerType("small Rose");
        ft.addSubType(ft2);
        Flower flower = ft2.createInstance();
        assertTrue(ft.hasInstance(flower));
        assertTrue(ft2.hasInstance(flower));
    }

    @Test
    public void testIsSubType(){
        FlowerType ft = new FlowerType("Rose");
        FlowerType ft2 = new FlowerType("small Rose");
        ft.addSubType(ft2);

        assertTrue(ft2.isSubType(ft));
        assertFalse(ft.isSubType(ft2));
        assertFalse(ft.isSubType(ft));

        try{
            ft.addSubType(null);
        }catch (IllegalArgumentException e){

        }
    }
}
