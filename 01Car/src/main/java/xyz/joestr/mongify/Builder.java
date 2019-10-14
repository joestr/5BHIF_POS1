/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.mongify;

/**
 *
 * @author Joel
 */
public class Builder {
    
    public static xyz.joestr.mongify.database.Builder getDatabaseBuilder() {
        return new xyz.joestr.mongify.database.Builder();
    }
    
    public static xyz.joestr.mongify.manipulator.Builder getManipulatorBuilder() {
        return new xyz.joestr.mongify.manipulator.Builder();
    }
}
