package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SphericCoordinate extends AbstractCoordinate{

    private double phi;
    private double theta;
    private double radius;

    public void assertClassInvariants(double phi, double theta, double radius){
        assert radius >= 0: "radius should >= 0";
        assert theta >= 0: "theta should >= 0";
        assert theta <= Math.PI: "theta should <= Math.PI";
        assert phi >= 0: "phi should >= 0";
        assert phi <= 2*Math.PI: "phi should <= 2*Math.PI";
    }

    // Constructor
    public SphericCoordinate(double phi, double theta, double radius){
        // Contracts
        assertClassInvariants(phi, theta, radius);

        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
    }

    // getter
    public double getPhi(){
        return this.phi;
    }

    public double getTheta(){
        return this.theta;
    }

    public double getRadius(){
        return this.radius;
    }

    // setter
    public void setPhi(double phi){
        assertClassInvariants(phi, this.theta, this.radius);
        this.phi = phi;
    }

    public void setTheta(double theta){
        assertClassInvariants(this.phi, theta, this.radius);
        this.theta = theta;
    }

    public void setRadius(double radius){
        assertClassInvariants(this.phi, this.theta, radius);
        this.radius = radius;
    }


    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants(this.phi, this.theta, this.radius);
        double x = this.radius * Math.sin(this.theta) * Math.cos(this.phi);
        double y = this.radius * Math.sin(this.theta) * Math.sin(this.phi);
        double z = this.radius * Math.cos(this.theta);
        return new CartesianCoordinate(x, y, z);
    }

    @Override
    public double getCartesianDistance(Coordinate coordinate) {
        assert coordinate instanceof SphericCoordinate || coordinate instanceof CartesianCoordinate: "the Coordinate must be cartesian or spherical";
        CartesianCoordinate cartesianCoordinate = coordinate.asCartesianCoordinate();
        return cartesianCoordinate.getCartesianDistance(this);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public double getCentralAngel(Coordinate coordinate) {
        assert coordinate instanceof SphericCoordinate || coordinate instanceof CartesianCoordinate: "the Coordinate must be cartesian or spherical";
        SphericCoordinate sphericCoordinate = coordinate.asSphericCoordinate();
        double delta_phi = Math.abs(sphericCoordinate.phi - this.phi);
        double delta_theta = Math.abs(sphericCoordinate.theta - this.theta);
        return 2 * Math.asin(Math.sqrt(Math.sin(delta_phi / 2) * Math.sin(delta_phi / 2) + Math.cos(sphericCoordinate.phi) * Math.cos(this.phi) * Math.sin(delta_theta / 2) * Math.sin(delta_theta / 2)));
    }

    @Override
    public  int hashCode() {
        return Objects.hash(phi, theta, radius);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        double x = rset.getDouble("coordinate_x");
        double y = rset.getDouble("rdinate_y");
        double z = rset.getDouble("coordinate_z");
        CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(x, y, z);
        SphericCoordinate sphericCoordinate = cartesianCoordinate.asSphericCoordinate();
        this.theta = sphericCoordinate.getTheta();
        this.phi = sphericCoordinate.getPhi();
        this.radius = sphericCoordinate.getRadius();
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        CartesianCoordinate cartesianCoordinate = this.asCartesianCoordinate();
        double x = cartesianCoordinate.getX();
        double y = cartesianCoordinate.getY();
        double z = cartesianCoordinate.getZ();
        rset.updateDouble("coordinate_x", x);
        rset.updateDouble("coordinate_y", y);
        rset.updateDouble("coordinate_z", z);
    }

}
