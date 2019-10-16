/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.school._5bhif._01animaladmin;

import java.time.LocalDate;
import org.bson.types.ObjectId;

/**
 *
 * @author Joel
 */
public class Animal {
    private ObjectId id;
    private String name;
    transient private LocalDate date;
    private String details;
}
