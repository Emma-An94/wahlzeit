package org.wahlzeit.model.flower;

public class Flower {

    protected FlowerType flowerType= null;

    public Flower(FlowerType ft) {
        flowerType = ft;
    }

    public FlowerType getType() {
        return flowerType;
    }
}
