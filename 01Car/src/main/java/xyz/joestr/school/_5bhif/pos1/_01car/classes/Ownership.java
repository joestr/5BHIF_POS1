/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.school._5bhif.pos1._01car.classes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.bson.types.ObjectId;

/**
 * @author Joel
 */
public class Ownership {
    
    private ObjectId _id;
    private transient LocalDate startDate, endDate;
    private ObjectId ownerId;

    public Ownership() {
    }

    public Ownership(LocalDate startDate, LocalDate endDate, ObjectId ownerId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.ownerId = ownerId;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId ownerId) {
        this._id = ownerId;
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

    public ObjectId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(ObjectId ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return startDate.format(format) + ", " + (endDate != null ? endDate.format(format) : "--.--.----") + ", " + ownerId;
    }
}
