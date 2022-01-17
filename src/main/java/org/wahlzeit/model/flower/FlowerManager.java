package org.wahlzeit.model.flower;

import org.wahlzeit.model.Photo;
import org.wahlzeit.model.PhotoId;

import java.util.HashMap;
import java.util.Map;

public class FlowerManager {

    /**
            * In-memory cache for flowers
	 */
    protected Map<FlowerType, Flower> flowers = new HashMap<FlowerType, Flower>();
    public Map<String, FlowerType> flowertypes = new HashMap<String, FlowerType>();

    private void assertIsValidFlowerTypeName(String typeName) throws IllegalArgumentException{
        if (typeName == null || typeName == "") {
            throw new IllegalArgumentException("FlowerType should not be empty.");
        }
    }

    public FlowerType getFlowerType(String typeName){
        assertIsValidFlowerTypeName(typeName);
        FlowerType ft = flowertypes.get(typeName);
        if (ft == null){
            ft = new FlowerType(typeName);
            flowertypes.put(typeName, ft);
        }
        return ft;
    }

    public Flower createFlower(String typeName){
        assertIsValidFlowerTypeName(typeName);
        FlowerType ft = getFlowerType(typeName);
        Flower result = ft.createInstance();
        flowers.put(result.getType(), result);
        return result;
    }
}
