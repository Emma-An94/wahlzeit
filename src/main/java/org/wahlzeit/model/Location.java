package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location extends DataObject{
    private Coordinate coordinate;
    private int location_id = 0; // default id

    public Location(){
        this.coordinate = new CartesianCoordinate(0.0,0.0,0.0);// set default coordinate
    }
    public Location(double x, double y, double z, int location_id){
        this.coordinate = new CartesianCoordinate(x, y, z); //Every location has exact one coordinate.
        this.location_id = location_id;
    }

    public Location(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public String getIdAsString() {
        return String.valueOf(this.location_id);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        this.coordinate.asCartesianCoordinate().readFrom(rset);
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        this.coordinate.asCartesianCoordinate().writeOn(rset);
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {
        stmt.setInt(pos, location_id);
    }
}
