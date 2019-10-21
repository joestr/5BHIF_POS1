/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.school._5bhif._01animaladmin;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import org.bson.types.ObjectId;

/**
 *
 * @author admin
 */
public class Animal {
    private ObjectId _id;
    private String name;
    transient private LocalDate adate;
    private String adetails;

    public Animal(String name, LocalDate adate, String adetails) {
        this.name = name;
        this.adate = adate;
        this.adetails = adetails;
    }

    public ObjectId getAid() {
        return _id;
    }

    public void setAid(ObjectId aid) {
        this._id = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getAdate() {
        return adate;
    }

    public void setAdate(Date adate) {
        this.adate = adate.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();
    }

    public String getAdetails() {
        return adetails;
    }

    public void setAdetails(String adetails) {
        this.adetails = adetails;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this._id);
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
        final Animal other = (Animal) obj;
        if (!Objects.equals(this._id, other._id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Animal{" + "_id=" + _id + ", name=" + name + ", adate=" + adate + ", adetails=" + adetails + '}';
    }

    
    
}
