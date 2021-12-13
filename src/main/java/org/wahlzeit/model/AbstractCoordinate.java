package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract  class AbstractCoordinate extends DataObject implements Coordinate{

    public abstract CartesianCoordinate asCartesianCoordinate();

    public abstract double getCartesianDistance(Coordinate coordinate);

    public boolean isEqual(Coordinate coordinate) {
        // precondition
        if (coordinate == null){
            throw new IllegalArgumentException("Object Coordinate should not be null");
        }
        if (false == (coordinate instanceof SphericCoordinate || coordinate instanceof CartesianCoordinate)){
            throw new IllegalArgumentException("the Coordinate must be cartesian or spherical");
        }

        CartesianCoordinate cartesianCoordinate = coordinate.asCartesianCoordinate();
        return asCartesianCoordinate().getCartesianDistance(cartesianCoordinate) <= 0.001;
    }

    public boolean equals(Object obj){
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        return isEqual((Coordinate) obj);
    }

    public abstract int hashCode();

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
    }

}
