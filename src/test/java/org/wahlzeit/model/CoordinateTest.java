package org.wahlzeit.model;

import org.junit.Test;

import java.util.HashSet;

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
    public void testIsEqual() {
        Coordinate coordinate1 = new CartesianCoordinate(0, 0, 0);
        assertTrue(cartesianCoordinate1.isEqual(coordinate1));
        assertTrue(cartesianCoordinate1.equals(coordinate1));

        Coordinate coordinate2 = new CartesianCoordinate(2, 2, 2);
        assertFalse(cartesianCoordinate1.isEqual(coordinate2));
        assertFalse(coordinate2.equals(2));

        assertFalse(sphericCoordinate.isEqual(sphericCoordinate1));
        assertTrue(sphericCoordinate.equals(sphericCoordinate));
        assertFalse(sphericCoordinate.equals(sphericCoordinate1));
        assertTrue(sphericCoordinate.getClass() == coordinate1.asSphericCoordinate().getClass());
    }

    @Test
    public void testDesignByContract(){
        try{
            Coordinate coordinate = new SphericCoordinate(-1, -1, -1);
        }catch(IllegalArgumentException e){

        }

        try{
            Coordinate obj = new CartesianCoordinate(0, 0, 0);
            cartesianCoordinate.getCartesianDistance(obj);
        }catch (IllegalArgumentException e){

        }
    }

    @Test
    public void testIllegalArgument(){
        Coordinate coordinate_null = null;
        try{
            cartesianCoordinate.isEqual(coordinate_null);
        }catch (IllegalArgumentException e){

        }
        try{
            double distance = sphericCoordinate1.getCartesianDistance(coordinate_null);
        }catch (IllegalArgumentException e){

        }
        try {
            double angle = sphericCoordinate.getCentralAngel(coordinate_null);
        }catch (IllegalArgumentException e){

        }
    }

    @Test
    public void testValueObject(){
        HashSet<Coordinate> co1 = new HashSet<Coordinate>();
        CartesianCoordinate c1 = new CartesianCoordinate(1,1,1);
        CartesianCoordinate c2 = new CartesianCoordinate(1,1,1);
        CartesianCoordinate c3 = new CartesianCoordinate(1,1,2);
        SphericCoordinate s1 = new SphericCoordinate(1,1,1);
        SphericCoordinate s2 = new SphericCoordinate(1,1,1);
        SphericCoordinate s3 = new SphericCoordinate(1,1,2);

        co1.add(c1);
        co1.add(c2);
        co1.add(c3);
        co1.add(s1);
        co1.add(s2);
        co1.add(s3);

        assertEquals(co1.size(), 4);

    }
}
