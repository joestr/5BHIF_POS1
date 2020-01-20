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
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Indexes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.Document;
import org.bson.types.ObjectId;
import pkgMisc.LocalDateTimeSerializer;
import pkgMisc.ObjectIdSerializer;

/**
 *
 * @author admin
 */
public class Database {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 27017;
    private static final String COLLECTIONNAME = "Position";
    private static final String DATABASENAME = "mydb";
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> positionCollection;
    private static Database database = null;

    private Database() throws Exception {

    }

    public static Database getInstance() throws Exception {
        if (database == null) {
            database = new Database();
            mongoClient = new MongoClient(IP, PORT);
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

    public ArrayList<MongoLogPositionEntry> getLatestPositions() {
        ArrayList<MongoLogPositionEntry> collPositions = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
                .create();
        ArrayList<String> collShipnames = positionCollection.distinct("shipName", String.class).into(new ArrayList());
        for (String strName : collShipnames) {
            Document first = positionCollection.find(eq("shipName", strName)).sort(Indexes.descending("datetime")).first();
            collPositions.add(gson.fromJson(first.toJson(), MongoLogPositionEntry.class));
        }
        return collPositions;
    }

    public ArrayList<String> getAvailableShips() {
        return positionCollection.distinct("shipName", String.class).into(new ArrayList());
    }

    public MongoLogPositionEntry getLastShipPosition(String nameShip) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
                .create();

        Document first = positionCollection.find(eq("shipName", nameShip)).sort(Indexes.descending("datetime")).first();
        return gson.fromJson(first.toJson(), MongoLogPositionEntry.class);
    }

    public double getDistanceBetween(String nameShip, SpatialPosition spPositionTarget) throws Exception {
        MongoLogPositionEntry shPos = getLastShipPosition(nameShip); //to get oid of document
        // The final command object
        BasicDBObject distance = new BasicDBObject();
        // Represents the parameters of the command
        BasicDBObject distanceData = new BasicDBObject();
        // The origin location has to be provided
        // if Mongo-Position is given then
        // Double[] loc =
        //     shPos.getPosition().getPosition().getValues().toArray(
        //        new Double[0]);
        Double[] loc = {spPositionTarget.getLongitude(), spPositionTarget.getLatitude()};
        distanceData.append("near", loc);
        distanceData.append("spherical", true);
        // With "query" we can limit the geospatial query to specific docs
        distanceData.append(
                "query",
                Filters.eq("_id", shPos.getId())
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
                = mongoDatabase.getCollection(COLLECTIONNAME)
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
