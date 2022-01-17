package org.wahlzeit.model.flower;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class FlowerType extends DataObject {

    protected FlowerType superType = null;
    protected Set<FlowerType> subTypes = new HashSet<FlowerType>();
    protected String type = null;

    public FlowerType(String typeName){
        this.type = typeName;
    }

    public FlowerType getSuperType(){
        return superType;
    }

    private void setSuperType(FlowerType ft){
        superType = ft;
    }

    public Iterator<FlowerType> getSubTypeIterator(){
        return subTypes.iterator();
    }

    public void addSubType(FlowerType ft) throws IllegalArgumentException{
        if (ft == null){
            throw new IllegalArgumentException("tried to set null sub-type!");
        }
        ft.setSuperType(this);
        subTypes.add(ft);
    }

    public boolean isSubType(FlowerType ft){
        return superType == ft;
    }

    public Flower createInstance(){
        return new Flower(this);
    }

    public boolean hasInstance(Flower flower) throws IllegalArgumentException{
        if (flower == null){
            throw new IllegalArgumentException("asked about null object");
        }
        if (flower.getType() == this){
            return true;
        }
        for (FlowerType type: subTypes){
            if (type.hasInstance(flower)){
                return true;
            }
        }
        return false;
    }
    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {

    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {

    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }
}
