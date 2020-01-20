/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 *
 * @author admin
 */
public class MongoLogPositionEntry {

    private ObjectId _id;
    private String shipName;
    private SpatialPosition position;
    private LocalDateTime datetime;

    public MongoLogPositionEntry(String shipName, SpatialPosition position, LocalDateTime datetime) {
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

    public SpatialPosition getPosition() {
        return position;
    }

    public void setPosition(SpatialPosition position) {
        this.position = position;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this._id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MongoLogPositionEntry other = (MongoLogPositionEntry) obj;
        if (!Objects.equals(this._id, other._id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return shipName + ": " + position.getLongitude() + " |  " + position.getLatitude() + ", " + datetime.format(DateTimeFormatter.ofPattern("HH:mm:ss, dd.MM.YYYY"));
    }
}
