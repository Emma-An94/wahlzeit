package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CartesianCoordinate extends AbstractCoordinate{
    private double x;
    private double y;
    private double z;

    //Constructor

    public CartesianCoordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //getter and setter
    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setZ(double z) {
        this.z = z;
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
        assert coordinate instanceof SphericCoordinate || coordinate instanceof CartesianCoordinate: "the Coordinate must be cartesian or spherical";
        CartesianCoordinate cartesiancoordinate = coordinate.asCartesianCoordinate();
        double dis_x = this.x - cartesiancoordinate.x;
        double dis_y = this.y - cartesiancoordinate.y;
        double dis_z = this.z - cartesiancoordinate.z;
        return Math.sqrt(dis_x * dis_x + dis_y * dis_y + dis_z * dis_z);
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
        assert coordinate instanceof SphericCoordinate || coordinate instanceof CartesianCoordinate: "the Coordinate must be cartesian or spherical";
        SphericCoordinate sphericCoordinate = coordinate.asSphericCoordinate();
        return sphericCoordinate.getCentralAngel(this);
    }

    @Override
    public  int hashCode(){
        return Objects.hash(x, y, z);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        x = rset.getDouble("coordinate_x");
        y = rset.getDouble("rdinate_y");
        z = rset.getDouble("coordinate_z");
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateDouble("coordinate_x", this.x);
        rset.updateDouble("coordinate_y", this.y);
        rset.updateDouble("coordinate_z", this.z);
    }

}
