/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01cargeo.classes;

import com.mongodb.client.model.geojson.Point;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Gerald
 */
public class PetrolStation {
    private ObjectId _id;
    private String stationName;
    private List<FuelType> providedFuelTypes;
    private Point position;

    public PetrolStation(String stName, List<FuelType> providedFuelTypes, Point position) {
        this.stationName = stName;
        this.providedFuelTypes = providedFuelTypes;
        this.position = position;
    }

    public ObjectId getOid() {
        return _id;
    }

    public void setOid(ObjectId oid) {
        this._id = oid;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public List<FuelType> getProvidedFuelTypes() {
        return providedFuelTypes;
    }

    public void setProvidedFuelTypes(List<FuelType> providedFuelTypes) {
        this.providedFuelTypes = providedFuelTypes;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return stationName + ", " + providedFuelTypes + ", " + position + " (" + _id + ")";
    }
    
    
}
