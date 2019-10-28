/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.school._5bhif.pos1._01car.classes;

import java.text.SimpleDateFormat;
import org.bson.types.ObjectId;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Joel
 */
public class Owner {
    private ObjectId _id;
    private String name, details;
    private LocalDate birth;

    public Owner() {
    }

    public Owner(String name, String details, LocalDate birth) {
        this.name = name;
        this.details = details;
        this.birth = birth;
    }
    
    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    void setBirth(Date birthDate) {
        this.birth = birthDate.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();
    }
    
    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return name + ", " + details + ", " + birth.format(format);
    }
}
