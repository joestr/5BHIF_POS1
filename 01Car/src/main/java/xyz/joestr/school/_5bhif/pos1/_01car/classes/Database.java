/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.school._5bhif.pos1._01car.classes;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.Collection;
import org.bson.Document;
import org.bson.types.ObjectId;
/**
 *
 * @author admin
 */
public class Database {
    private static String CONNECTIONSTRING;
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static Database database = null;
    public static Database getInstance(String ip) throws Exception {
        if (database == null) {
            database = new Database();
            CONNECTIONSTRING = ip;
            mongoClient = new MongoClient(CONNECTIONSTRING, 27017);
            mongoDatabase = mongoClient.getDatabase("mydb");
        }
        return database;
    }
    private Database() throws Exception {
    }
    public void close() throws Exception {
        mongoClient.close();
        database = null;
    }
    
        
    public ObjectId insertCart(Car car) {
        Gson gson = new Gson();
        
        String jsonString = gson.toJson(car);
        MongoCollection mcoll = mongoDatabase.getCollection("Cars");
        Document doc = Document.parse(jsonString);
        mcoll.insertOne(doc);
        car.setId(doc.get("_id", ObjectId.class));
        
        return (ObjectId) doc.get("_id");
    }
    
    public Collection<Car> getAllCars() {
        
        MongoCollection<Document> mc = mongoDatabase.getCollection("Cars");
        
        Collection<Car> r = new ArrayList<>();
        
        for(Document doc : mc.find()) {
             r.add(new Gson().fromJson((doc.toJson()), Car.class));
        }
        
        return r;
    }
    
    public boolean updateCar(Car oldCar, Car newCar) {
        
        MongoCollection mc = mongoDatabase.getCollection("Cars");

        return mc.updateOne(Document.parse(new Gson().toJson(oldCar)), Document.parse(new Gson().toJson(newCar))).wasAcknowledged();
    }
    
    public boolean replaceCar(Car oldCar, Car newCar) {
        
        MongoCollection mc = mongoDatabase.getCollection("Cars");

        return mc.replaceOne(Document.parse(new Gson().toJson(oldCar)), newCar).wasAcknowledged();
    }
}