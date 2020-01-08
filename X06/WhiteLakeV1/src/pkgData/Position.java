/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;
import com.mongodb.client.model.geojson.Point;
import java.time.LocalDateTime;
import org.bson.types.ObjectId;

/**
 *
 * @author schueler
 */
public class Position {
    private ObjectId _id;
    private String shipName;
    private Point position;
    private LocalDateTime datetime;

    public Position(String shipName, Point position, LocalDateTime datetime) {
        this.shipName = shipName;
        this.position = position;
        this.datetime = datetime;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
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
        return "Position{" + "shipName=" + shipName + ", position=" + position + ", datetime=" + datetime + '}';
    }
}
