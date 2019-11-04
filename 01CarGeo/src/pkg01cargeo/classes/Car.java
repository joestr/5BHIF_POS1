/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01cargeo.classes;

import pkg01cargeo.classes.GeoPosition;
import org.bson.types.ObjectId;

/**
 *
 * @author Gerald
 */
public class Car {
    private ObjectId _id;
    private String carName;
    private GeoPosition position;

    public Car(String carName, GeoPosition position) {
        this.carName = carName;
        this.position = position;
    }

    public ObjectId getOid() {
        return _id;
    }

    public void setOid(ObjectId oid) {
        this._id = oid;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public GeoPosition getPosition() {
        return position;
    }

    public void setPosition(GeoPosition position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return carName + ", " + position + " (" + _id + ")";
    }
    
    
}
