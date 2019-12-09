/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htl_villach.edu.strassej._06shipserver.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author admin
 */
public class Database {

    private static String IP = "127.0.0.1";
    private static final String COLLECTIONNAME = "Position";
    private static final String DATABASENAME = "mydb";
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> positionCollection;
    private static Database database = null;

    private static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

        @Override
        public JsonElement serialize(LocalDateTime date, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();
            if (date != null) {
                json.addProperty("$date", date.toInstant(ZoneOffset.UTC).toEpochMilli());
            }
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

    private Database() throws Exception {

    }

    public static Database getInstance() throws Exception {
        if (database == null) {
            database = new Database();
            mongoClient = new MongoClient(IP, 27017);
            mongoDatabase = mongoClient.getDatabase(DATABASENAME);
            positionCollection = mongoDatabase.getCollection(COLLECTIONNAME);
        }
        return database;
    }

    public ObjectId insertPosition(MongoLogPositionEntry position) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
                .create();
        String jsonString = gson.toJson(position);
        Document doc = Document.parse(jsonString);
        positionCollection.insertOne(doc); //sync
        position.setId((ObjectId) doc.get("_id"));
        return (ObjectId) doc.get("_id");
    }
}
