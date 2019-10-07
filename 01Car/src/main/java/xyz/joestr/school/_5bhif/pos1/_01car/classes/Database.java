/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.school._5bhif.pos1._01car.classes;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
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
    
    public long updateCar(Car car) {
        
        MongoCollection mc = mongoDatabase.getCollection("Cars");

        Document doc = Document.parse(new Gson().toJson(car));
        doc.remove("_id");
        
        return mc.updateOne(Filters.eq("_id", car.getId()), new Document("$set", doc)).getModifiedCount();
    }
    
    public long updateCarAlt(Car car) {
        MongoCollection mc = mongoDatabase.getCollection("Cars");

        Document doc = Document.parse(new Gson().toJson(car));
        doc.remove("_id");
        
        return mc.updateOne(Filters.eq("_id", car.getId()), doc).getModifiedCount();
    }
    
    public long replaceCar(Car car) {
        
        MongoCollection mc = mongoDatabase.getCollection("Cars");

        Document doc = Document.parse(new Gson().toJson(car));
        doc.remove("_id");
        
        return mc.replaceOne(Filters.eq("_id", car.getId()), new Document("$set", doc)).getModifiedCount();
    }
    
    public long deleteCar(Car car) {
        MongoCollection mc = mongoDatabase.getCollection("Cars");
        
        return mc.deleteOne(Filters.eq("_id", car.getId())).getDeletedCount();
    }
    
    public void createTextIndex() throws Exception {
        // splits text in tokens; updated automatically
        MongoCollection mc = mongoDatabase.getCollection("Cars");
        mc.createIndex(Indexes.text("description"));
    }
    
    public Collection<Car> getAllCarsOrderByRelevance(String strFilter) throws Exception {
        Collection<Car> result = new ArrayList<>();
        Gson gson = new Gson();
        
        MongoCollection<Document> c = mongoDatabase.getCollection("Cars");
        
        ArrayList<Document> cD = c.find(
            new Document(
                "$text",
                new Document(
                    "$search",
                    strFilter
                ).append(
                    "$caseSenesitive",
                    false
                ).append(
                    "$diacriticSensitive",
                    false
                )
            )
        ).projection(
            Projections.metaTextScore("score")
        ).sort(
            Sorts.metaTextScore("score")
        ).into(
            new ArrayList<>()
        );
        
        for(Document d : cD) {
            String jS = d.toJson();
            Car car = gson.fromJson(jS, Car.class);
            car.setId(d.get("_id", ObjectId.class));
            result.add(car);
        }
        
        return result;
    }
    
    public Collection<Owner> getAllOwners() {
        return null;
    }
}