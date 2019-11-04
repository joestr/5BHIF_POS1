/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01cargeo.classes;

import com.mongodb.client.model.geojson.Point;
import java.time.LocalDateTime;

/**
 *
 * @author Gerald
 */
public class GeoPosition {
    private Point location;
    private LocalDateTime datetime;

    public GeoPosition(Point location, LocalDateTime datetime) {
        this.location = location;
        this.datetime = datetime;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return "GeoPosition{loc=" + location + ", datetime=" + datetime + '}';
    }
    
}
