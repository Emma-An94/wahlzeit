package org.wahlzeit.model;

public class Coordinate {
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
        return this.x == coordinate.x && this.y == coordinate.y && this.z == coordinate.z;
    }

}