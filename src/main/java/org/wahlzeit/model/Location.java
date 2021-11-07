package org.wahlzeit.model;

public class Location {
    private Coordinate coordinate;

    public Location(){
        this.coordinate = new Coordinate(0.0,0.0,0.0);// set default coordinate
    }
    public Location(double x, double y, double z){
        this.coordinate = new Coordinate(x, y, z); //Every location has exact one coordinate.
    }

    public Location(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }
}
