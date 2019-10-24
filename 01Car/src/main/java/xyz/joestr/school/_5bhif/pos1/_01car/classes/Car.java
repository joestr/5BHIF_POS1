/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.school._5bhif.pos1._01car.classes;

import java.util.ArrayList;
import java.util.Collection;
import org.bson.types.ObjectId;



/**
 *
 * @author org
 */
public class Car {
    private ObjectId _id;
    private String name;
    private int hp, year;
    private String description;
    private transient Collection<Ownership> ownerships = new ArrayList<>();

    public Car(String name, int hp, int year, String description) {
        this.name = name;
        this.hp = hp;
        this.year = year;
        this.description = description;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }
    

    @Override
    public String toString() {
        int length = Math.min(description.length(), 50);
        return name + ", " + year + ", " + hp + ", " + description.substring(0, length) + "...";
    }

    public String toLongString() {
        return "Car{id=" + _id + ", name=" + name + ", hp=" + hp + ", year=" + year + ", description=" + description + '}';
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Collection<Ownership> getOwnerships() {
        return this.ownerships;
    }

    public void setOwner(Collection<Ownership> ownerships) {
        this.ownerships = ownerships;
    }
}
