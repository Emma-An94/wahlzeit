package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


@PatternInstance(
    patternName = "SingleTon Pattern",
    participants = {
        "Class", "ConcreteInstance"
    }
)
public class SphericCoordinate extends AbstractCoordinate{

    private final double phi;
    private final double theta;
    private final double radius;

    public void assertClassInvariants() throws IllegalArgumentException{
        if (this.radius < 0){
            throw new IllegalArgumentException("radius should >= 0");
        }
        if (this.theta < 0){
            throw new IllegalArgumentException("theta should >= 0");
        }
        if (this.theta > Math.PI){
            throw new IllegalArgumentException("theta should <= Math.PI");
        }
        if (this.phi < 0){
            throw new IllegalArgumentException("phi should >= 0");
        }
        if (this.phi > 2*Math.PI){
            throw new IllegalArgumentException("phi should <= 2*Math.PI");
        }
    }

    // Constructor
    public SphericCoordinate(double phi, double theta, double radius){
        this.phi = phi;
        this.theta = theta;
        this.radius = radius;
        // post condition
        assertClassInvariants();
        asCartesianCoordinate();
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
    public SphericCoordinate setPhi(double phi){

        SphericCoordinate s = new SphericCoordinate(phi, this.theta, this.radius);

        // post condition
        assertClassInvariants();

        return s;
    }

    public SphericCoordinate setTheta(double theta){

        SphericCoordinate s = new SphericCoordinate(this.phi, theta, this.radius);

        // post condition
        assertClassInvariants();

        return s;
    }

    public SphericCoordinate setRadius(double radius){

        SphericCoordinate s = new SphericCoordinate(this.phi, this.theta, radius);

        // post condition
        assertClassInvariants();

        return s;
    }


    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        // precondition
        assertClassInvariants();

        double x = this.radius * Math.sin(this.theta) * Math.cos(this.phi);
        double y = this.radius * Math.sin(this.theta) * Math.sin(this.phi);
        double z = this.radius * Math.cos(this.theta);
        return new CartesianCoordinate(x, y, z);
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

        CartesianCoordinate cartesianCoordinate = coordinate.asCartesianCoordinate();
        return cartesianCoordinate.getCartesianDistance(this);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public double getCentralAngel(Coordinate coordinate) {
        // precondtion
        if (coordinate == null){
            throw new IllegalArgumentException("Object Coordinate should not be null");
        }
        if (false == (coordinate instanceof SphericCoordinate || coordinate instanceof CartesianCoordinate)){
            throw new IllegalArgumentException("the Coordinate must be cartesian or spherical");
        }
        SphericCoordinate sphericCoordinate = coordinate.asSphericCoordinate();
        double delta_phi = Math.abs(sphericCoordinate.phi - this.phi);
        double delta_theta = Math.abs(sphericCoordinate.theta - this.theta);
        return 2 * Math.asin(Math.sqrt(Math.sin(delta_phi / 2) * Math.sin(delta_phi / 2) + Math.cos(sphericCoordinate.phi) * Math.cos(this.phi) * Math.sin(delta_theta / 2) * Math.sin(delta_theta / 2)));
    }

    @Override
    public  int hashCode() {
        return Objects.hash(phi, theta, radius);
    }

}
