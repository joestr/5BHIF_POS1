/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07plf_client.data;

import com.mongodb.client.model.geojson.Point;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;

/**
 *
 * @author Gerald
 */
public class DriverPosition {
    private ObjectId _id;
    private String driverName;
    private Point position;
    private LocalDateTime datetime; 

    public DriverPosition(ObjectId _id, String driverName, Point position, LocalDateTime datetime) {
        this._id = _id;
        this.driverName = driverName;
        this.position = position;
        this.datetime = datetime;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "DriverPosition{" + "_id=" + _id + ", driverName=" + driverName + ", position=" + position + ", datetime=" + datetime + '}';
    }
    
    
}
