package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

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
    public void readFrom(ResultSet rset) throws SQLException {
        double x = rset.getDouble("coordinate_x");
        double y = rset.getDouble("rdinate_y");
        double z = rset.getDouble("coordinate_z");
        // simply try to add the read result into hashset
        new CartesianCoordinate(x, y, z);
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

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
    }

}
