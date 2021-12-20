package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CartesianCoordinate extends AbstractCoordinate{
    private final double x;
    private final double y;
    private final double z;

    //Constructor

    public CartesianCoordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //getter and setter
    public CartesianCoordinate setX(double x) {
        return new CartesianCoordinate(x, this.y, this.z);
    }

    public double getX() {
        return x;
    }

    public CartesianCoordinate setY(double y) {
        return new CartesianCoordinate(this.x, y, this.z);
    }

    public double getY() {
        return y;
    }

    public CartesianCoordinate setZ(double z) {
        return new CartesianCoordinate(this.x, this.y, z);
    }

    public double getZ() {
        return z;
    }


    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return this;
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        // precondition
        if (coordinate == null){
            throw new IllegalArgumentException("Object Coordinate should not be null");
        }
        if (false == (coordinate instanceof SphericCoordinate || coordinate instanceof CartesianCoordinate)){
            throw new IllegalArgumentException("the Coordinate must be cartesian or spherical");
        }

        CartesianCoordinate cartesiancoordinate = coordinate.asCartesianCoordinate();
        double dis_x = this.x - cartesiancoordinate.x;
        double dis_y = this.y - cartesiancoordinate.y;
        double dis_z = this.z - cartesiancoordinate.z;
        double result = Math.sqrt(dis_x * dis_x + dis_y * dis_y + dis_z * dis_z);
        // post condition
        if (result < 0){
            throw new IllegalStateException("Calculation wrong, CartesianDistance must be >= 0");
        }
        return result;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        double radius = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        if (radius <= 0.001){
            return new SphericCoordinate(0, 0, 0);
        }
        double phi = Math.acos(this.z / radius);
        double theta = Math.atan(this.y / this.x);
        return new SphericCoordinate(phi, theta,radius);
    }

    @Override
    public double getCentralAngel(Coordinate coordinate) {
        // preconditon
        if (coordinate == null){
            throw new IllegalArgumentException("Object Coordinate should not be null");
        }
        if (false == (coordinate instanceof SphericCoordinate || coordinate instanceof CartesianCoordinate)){
            throw new IllegalArgumentException("the Coordinate must be cartesian or spherical");
        }

        SphericCoordinate sphericCoordinate = coordinate.asSphericCoordinate();
        return sphericCoordinate.getCentralAngel(this);
    }

    @Override
    public  int hashCode(){
        return Objects.hash(x, y, z);
    }

}
