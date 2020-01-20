/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

/**
 *
 * @author admin
 */
public class DistanceBetween {
    private String shipName;
    private SpatialPosition targetPosition;
    private double distance;

    public DistanceBetween(String shipName, SpatialPosition targetPosition, double distance) {
        this.shipName = shipName;
        this.targetPosition = targetPosition;
        this.distance = distance;
    }

    public DistanceBetween(String shipName, SpatialPosition targetPosition) {
        this.shipName = shipName;
        this.targetPosition = targetPosition;
    }
    
    

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public SpatialPosition getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(SpatialPosition targetPosition) {
        this.targetPosition = targetPosition;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    
}
