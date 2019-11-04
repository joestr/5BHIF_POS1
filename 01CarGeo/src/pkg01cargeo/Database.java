/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01cargeo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;
import pkg01cargeo.classes.Car;
import pkg01cargeo.classes.PetrolStation;
import com.mongodb.client.model.geojson.Position;
import com.mongodb.client.model.geojson.Point;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.function.Consumer;
import org.bson.types.ObjectId;
import pkg01cargeo.classes.GeoPosition;

/**
 *
 * @author admin
 */
public class Database {

    private static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime date, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();
            json.addProperty("$date", date.toInstant(ZoneOffset.UTC).toEpochMilli());
            return json;
        }

        @Override
        public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Instant.ofEpochMilli(jsonElement.getAsJsonObject().get("$date").getAsLong()).atZone(ZoneOffset.UTC).toLocalDateTime();
        }
    }

    private static class ObjectIdSerializer implements JsonSerializer<ObjectId>, JsonDeserializer<ObjectId> {
        @Override
        public JsonElement serialize(ObjectId id, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();
            json.addProperty("$oid", id.toHexString());
            return json;
        }

        @Override
        public ObjectId deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new ObjectId(jsonElement.getAsJsonObject().get("$oid").getAsString());
        }
    }
    
    private static class PointSerializer implements JsonSerializer<Point>, JsonDeserializer<Point> {

        @Override
        public JsonElement serialize(Point t, Type type, JsonSerializationContext jsc) {
            JsonObject jo = new JsonObject();
            JsonArray ja = new JsonArray();
            ja.add(t.getCoordinates().getValues().get(0));
            ja.add(t.getCoordinates().getValues().get(1));
            jo.addProperty("type", "Point");
            jo.add("coordinates", ja);
            return jo;
        }

        @Override
        public Point deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
            return new Point(
                new Position(
                    je.getAsJsonObject().get("coordinates").getAsJsonArray().get(0).getAsDouble(),
                    je.getAsJsonObject().get("coordinates").getAsJsonArray().get(1).getAsDouble()
                )
            );
        }
    
}

    private static final Gson GSON =
        new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTimeSerializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
            .registerTypeAdapter(Point.class, new PointSerializer())
            .create();
    
    private static final String DATABASENAME = "db_03CarGeo";
    private static final String CARCOLLECTION = "Cars";
    private static final String OWNERCOLLECTION = "PetrolStations";

    private static Database database = null;
    
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    private static MongoCollection<Document> carsCollection;
    private static MongoCollection<Document> petrolStationsCollection;
    
    private static ArrayList<Car> collCars = null;
    private static ArrayList<PetrolStation> collPetrolStations = null;

    public static Database getInstance(String ip) throws Exception {
        if (database == null) {
            database = new Database();
            
            mongoClient = new MongoClient(ip, 27017);
            
            mongoDatabase = mongoClient.getDatabase(DATABASENAME);
            
            carsCollection = mongoDatabase.getCollection(CARCOLLECTION);
            petrolStationsCollection = mongoDatabase.getCollection(OWNERCOLLECTION);
            
            collCars = new ArrayList<>();
            collPetrolStations = new ArrayList<>();
        }
        
        return database;
    }

    private Database() throws Exception {
    }

    /**
     * Closes the connection and sets database instance to {@code null}.
     * @throws Exception 
     */
    public void close() throws Exception {
        mongoClient.close();
        database = null;
    }

    public void generateData() throws Exception {
        collCars.clear();
        collCars.add(new Car("Isetta Völkermarkt",
                new GeoPosition(new Point(new Position(14.6, 46.7)),
                        LocalDateTime.of(1990, Month.MARCH, 31, 7, 45))));
        collCars.add(new Car("DKW Udine",
                new GeoPosition(new Point(new Position(13.2, 46)),
                        LocalDateTime.now())));
        collCars.add(new Car("Rover Lienz",
                new GeoPosition(new Point(new Position(12.8, 46.8)),
                        LocalDateTime.of(1999, Month.DECEMBER, 31, 23, 45))));
        collPetrolStations.clear();
        collPetrolStations.add(new PetrolStation("Esso Villach",
                new Point(new Position(13.9, 46.6))));
        collPetrolStations.add(new PetrolStation("Avanti Klagenfurt",
                new Point(new Position(14.3, 46.6))));
        collPetrolStations.add(new PetrolStation("LH Spittal",
                new Point(new Position(13.5, 46.8))));
    }

    public void saveToMongo() {
        collPetrolStations.forEach((petrolStation) -> {
            petrolStationsCollection.insertOne(
                Document.parse(
                    GSON.toJson(petrolStation)
                )
            );
        });
        
        collCars.forEach((car) -> {
            carsCollection.insertOne(
                Document.parse(
                    GSON.toJson(car)
                )
            );
        });
    }
    
    public void readFromMongo() {
        collCars.clear();
        
        carsCollection.find().forEach( new Consumer<Document>() {
            @Override
            public void accept(Document t) {
                collCars.add(GSON.fromJson(t.toJson(), Car.class));
            }
        });
        
        collPetrolStations.clear();
        
        petrolStationsCollection.find().forEach( new Consumer<Document>() {
            @Override
            public void accept(Document t) {
                collPetrolStations.add(GSON.fromJson(t.toJson(), PetrolStation.class));
            }
        });
    }
}
