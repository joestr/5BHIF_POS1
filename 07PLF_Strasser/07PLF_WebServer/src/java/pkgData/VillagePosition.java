/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07plf_client.data;

import com.mongodb.client.model.geojson.Point;
import org.bson.types.ObjectId;

/**
 *
 * @author Gerald
 */
public class VillagePosition {
    private ObjectId _id;
    private String villageName;
    private Point position;

    public VillagePosition(ObjectId _id, String driverName, Point position) {
        this._id = _id;
        this.villageName = driverName;
        this.position = position;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return "VillagePosition{" + "_id=" + _id + ", villageName=" + villageName + ", position=" + position + '}';
    }
    
    
}
