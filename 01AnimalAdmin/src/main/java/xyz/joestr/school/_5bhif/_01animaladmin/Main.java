/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.school._5bhif._01animaladmin;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import org.bson.BsonDateTime;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Joel
 */
public class Main {
    
    public static void main(String args[]) {
        
                MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("animaladmin");
        MongoCollection<Document> animalsCollection = mongoDatabase.getCollection("animals");

        Animal a = new Animal("Katze", LocalDate.of(2000, 10, 20), "Meins nix deins");
        Document doc = Document.parse(new Gson().toJson(a));
        doc.append("aDateISO",
                new BsonDateTime(a.getAdate().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000));
        animalsCollection.insertOne(doc);
        a.setAid(((ObjectId) doc.get("_id")));

        ArrayList<Animal> animals = new ArrayList<>();
        for (Document d : animalsCollection.find()) {
            String jsonString = d.toJson();
            Date tmpDate = d.get("aDateISO", Date.class);
            Animal animal = new Gson().fromJson(jsonString, Animal.class);
            animal.setAid((ObjectId) d.get("_id"));
            animal.setAdate(tmpDate);
            animals.add(animal);
        }
    }
}
