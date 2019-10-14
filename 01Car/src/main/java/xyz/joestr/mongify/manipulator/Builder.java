/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.mongify.manipulator;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author Joel
 */
public class Builder {
    
    Class<?> root;
    Class<?> embeded;
    
    MongoDatabase mongoDatabase;
    
    public Builder withDatabase(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
        return this;
    }
    
    public Builder withRoot(Class<?> root) {
        this.root = root;
        return this;
    }
    
    public Builder withEmbeded(Class<?> embeded) {
        this.embeded = embeded;
        return this;
    }
}
