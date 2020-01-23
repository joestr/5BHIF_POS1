/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Indexes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.Document;
import org.bson.types.ObjectId;
import pkg07plf_client.data.DriverPosition;
import pkg07plf_client.data.VillagePosition;
import pkgMisc.LocalDateTimeSerializer;
import pkgMisc.ObjectIdSerializer;

/**
 *
 * @author admin
 */
public class Database {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 27017;
    private static final String DATABASENAME = "gomobildb_strasser";
    private static final String VILLAGECOLLECTION = "villages";
    private static final String DRIVERCOLLECTION = "drivers";
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> villageCollection;
    private static MongoCollection<Document> driverCollection;
    private static Database database = null;

    private Database() {

    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
            mongoClient = new MongoClient(IP, PORT);
            mongoDatabase = mongoClient.getDatabase(DATABASENAME);
            villageCollection = mongoDatabase.getCollection(VILLAGECOLLECTION);
            driverCollection =  mongoDatabase.getCollection(DRIVERCOLLECTION);
            /*MongoCollection collection = mongoDatabase.getCollection(VILLAGECOLLECTION);
            if(collection != null) collection.drop();
            mongoDatabase.createCollection(VILLAGECOLLECTION);
            collection = mongoDatabase.getCollection(VILLAGECOLLECTION);
            collection.createIndex(Indexes.geo2dsphere("position.coordinate.values"));

            collection = mongoDatabase.getCollection(DRIVERCOLLECTION);
            if(collection != null) collection.drop();
            mongoDatabase.createCollection(DRIVERCOLLECTION);
            collection = mongoDatabase.getCollection(DRIVERCOLLECTION);
            collection.createIndex(Indexes.geo2dsphere("position.coordinate.values"));
            */
            }
        return database;
    }

    public ObjectId insertVillage(VillagePosition vp) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
                .create();
        String jsonString = gson.toJson(vp);
        Document doc = Document.parse(jsonString);
        villageCollection.insertOne(doc); //sync
        vp.setId((ObjectId) doc.get("_id"));
        return (ObjectId) doc.get("_id");
    }
    
    public ObjectId insertDriver(DriverPosition dp) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
                .create();
        String jsonString = gson.toJson(dp);
        Document doc = Document.parse(jsonString);
        driverCollection.insertOne(doc); //sync
        dp.setId((ObjectId) doc.get("_id"));
        return (ObjectId) doc.get("_id");
    }

    public DriverPosition getLastDriverPosition(String driverName) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
                .create();

        Document first = driverCollection.find(eq("driverName", driverName)).sort(Indexes.descending("datetime")).first();
        return gson.fromJson(first.toJson(), DriverPosition.class);
    }

    public double getNearestDriver(String cityName, int radius) throws Exception {
        ArrayList<DriverPosition> collPositions = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
                .create();
        ArrayList<String> collShipnames = driverCollection.distinct("driverName", String.class).into(new ArrayList());
        for (String strName : collShipnames) {
            Document first = driverCollection.find(eq("dirverName", strName)).sort(Indexes.descending("datetime")).first();
            collPositions.add(gson.fromJson(first.toJson(), DriverPosition.class));
        }
        // The final command object
        BasicDBObject distance = new BasicDBObject();
        // Represents the parameters of the command
        BasicDBObject distanceData = new BasicDBObject();
        // The origin location has to be provided
        // if Mongo-Position is given then
        // Double[] loc =
        //     shPos.getPosition().getPosition().getValues().toArray(
        //        new Double[0]);
        Double[] loc = {
            collPositions.get(0).getPosition().getPosition().getValues().get(0),
            collPositions.get(0).getPosition().getPosition().getValues().get(1)
        };
        distanceData.append("near", loc);
        distanceData.append("spherical", true);
        // With "query" we can limit the geospatial query to specific docs
        distanceData.append(
                "query",
                Filters.eq("_id", collPositions.get(0).getId())
        );
        // The maximal distance in meters
        distanceData.append("maxDistance", 80000);
        // Where the calculated distance will be available
        distanceData.append("distanceField", "__dist_calculated");
        // To get meters back (Why 6371000? average earths radius)
        distanceData.append("distanceMultiplier", 6371000);
        // add the parameters to the command
        distance.append("$geoNear", distanceData);
        AggregateIterable<Document> docs
                = mongoDatabase.getCollection(VILLAGECOLLECTION)
                        .aggregate(
                                Arrays.asList(
                                        distance
                                )
                        );
        double result;
        Document doc = docs.first();
        // Could be null so we stay on the safe side
        if (doc == null) {
            result = -1d;
        } else {
            result = docs.first().getDouble("__dist_calculated");
        }
        return result;
    }
}
