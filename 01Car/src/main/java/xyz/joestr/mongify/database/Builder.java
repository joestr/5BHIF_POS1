/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.mongify.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Joel
 */
public class Builder {
    
    private String ipAddress;
    private int port;
    private String collection;
    
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    
    /**
     * Specify the IP address of the connection.
     * @param ip The ip address.
     * @return This builder.
     */
    public Builder withIp(String ip) {
        this.ipAddress = ip;
        return this;
    }
    
    /**
     * Specify the port of the connection.
     * @param port The port number.
     * @return This builder.
     */
    public Builder withPort(int port) {
        this.port = port;
        return this;
    }
    
    /**
     * Specify the name of the collection to use.
     * @param collection The name of the collection.
     * @return This builder.
     */
    public Builder withCollection(String collection) {
        this.collection = collection;
        return this;
    }
    
    /**
     * Finishes this builder.
     * @return This builder.
     */
    public Builder build() {
        this.mongoClient = new MongoClient(this.ipAddress, this.port);
        this.mongoDatabase = this.mongoClient.getDatabase(this.collection);
        return this;
    }
    
    /**
     * Get the client of this builder.
     * @return The MongoClient of this builder.
     */
    public MongoClient getMongoClient() {
        return this.mongoClient;
    }
    
    /**
     * Get the database of this builder.
     * @return The MongoDatabase of this builder.
     */
    public MongoDatabase getMongoDatabase() {
        return this.mongoDatabase;
    }
}
