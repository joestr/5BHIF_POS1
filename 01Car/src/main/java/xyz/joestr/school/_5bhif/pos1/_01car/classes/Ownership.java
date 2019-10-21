/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.school._5bhif.pos1._01car.classes;

import java.time.LocalDate;
import org.bson.types.ObjectId;

/**
 *
 * @author Joel
 */
class Ownership {
    
    private transient LocalDate startDate, endDate;
    private ObjectId id;

    public Ownership() {
    }

    public Ownership(LocalDate startDate, LocalDate endDate, ObjectId ownerId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = ownerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId ownerId) {
        this.id = ownerId;
    }
    
    
}
