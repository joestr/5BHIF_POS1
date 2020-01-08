/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.geojson.Point;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.bson.Document;
import org.bson.types.ObjectId;
import pkgMisc.*;

/**
 *
 * @author schueler
 */
public class Database {
    private final static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
            .registerTypeAdapter(Point.class, new GsonPointSerializer())
            .registerTypeAdapter(LocalTime.class, new LocalTimeSerialize())
            .create();

    private static String CONNECTSTRING;
    private static MongoClient mongoClient = null;
    private static Database database = null;
    private static MongoDatabase mongodatabase = null;
    private String collNameShipPosition = "shipPosition";

    public static Database newInstance(String ip) throws Exception {
        if (database == null) {
            database = new Database();
            CONNECTSTRING = ip;
            mongoClient = new MongoClient(CONNECTSTRING, 27017);
            mongodatabase = mongoClient.getDatabase("WhiteLake");
        }

        return database;
    }
    
    public void insertRestaurant(Position position) {
        MongoCollection collection = mongodatabase.getCollection(collNameShipPosition);
        Document document = Document.parse(GSON.toJson(position));
        if (position.getId() == null) {
            position.setId(new ObjectId());
        }
        collection.insertOne(document);
    }
}
