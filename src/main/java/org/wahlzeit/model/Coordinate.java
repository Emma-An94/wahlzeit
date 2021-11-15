package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Coordinate extends DataObject {
    private double x;
    private double y;
    private double z;

    //Constructor

    public Coordinate(double x, double y, double z){
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

    public double getDistance(Coordinate coordinate){
        double dis_x = this.x - coordinate.x;
        double dis_y = this.y - coordinate.y;
        double dis_z = this.z - coordinate.z;
        return Math.sqrt(dis_x * dis_x + dis_y * dis_y + dis_z * dis_z);
    }

    public boolean isEqual(Coordinate coordinate){
        return getDistance(coordinate) <= 0.0001;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) return true;
        if (obj.getClass() != this.getClass()) return false;
        return isEqual((Coordinate) obj);
    }

    @Override
    public  int hashCode(){
        return Objects.hash(x, y, z);
    }

    @Override
    public String getIdAsString() {
        return null;
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

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException{
    }
}
