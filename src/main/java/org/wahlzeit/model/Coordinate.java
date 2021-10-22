package org.wahlzeit.model;

public class Coordinate {
    private double x;
    private double y;
    private double z;

    //Constructor


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

    public double[] getCoordinate() {
        double[] coordinate = new double[3];
        coordinate[0] = x;
        coordinate[1] = y;
        coordinate[2] = z;
        return coordinate;
    }

    public Coordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
