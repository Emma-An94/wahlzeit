package org.wahlzeit.model;

import java.util.HashSet;

public interface Coordinate {

    CartesianCoordinate asCartesianCoordinate();

    double getCartesianDistance(Coordinate coordinate);

    SphericCoordinate asSphericCoordinate();

    double getCentralAngel(Coordinate coordinate);

    boolean isEqual(Coordinate coordinate);
}
