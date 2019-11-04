/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01cargeo;

import com.mongodb.client.model.geojson.Point;
import org.bson.types.ObjectId;

/**
 *
 * @author Gerald
 */
public class PetrolStation {
    private ObjectId _id;
    private String stationName;
    private Point position;

    public PetrolStation(String stName, Point position) {
        this.stationName = stName;
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

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return stationName + ", " + position + " (" + _id + ")";
    }
    
    
}
