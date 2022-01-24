package org.wahlzeit.model.flower;

public class Flower {

    /*
    prototype scenario:
    FlowerManager#createFlower(String typeName) => assertIsValidFlowerTypeName(typeName) =(if valid)=>
    FlowerType#createInstance()=>
    Finally, the object ist created in FLowerType using command "new Flower(ft)" which calls the constructor in this class
     */
    protected FlowerType flowerType= null;

    public Flower(FlowerType ft) {
        flowerType = ft;
    }

    public FlowerType getType() {
        return flowerType;
    }
}
