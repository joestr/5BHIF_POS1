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
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.geojson.Point;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import org.bson.Document;
import org.bson.types.ObjectId;
import pkgMisc.*;

/**
 *
 * @author schueler
 */
public class Database {
    private final static Gson GSON =
        new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
            .registerTypeAdapter(Point.class, new GsonPointSerializer())
            .registerTypeAdapter(LocalTime.class, new LocalTimeSerialize())
            .create();

    private static Database database = null;
    
    private final String ip;
    private final int port;
    private final String databaseName;
    
    private final String collNameShipPosition = "shipPosition";
    
    private MongoClient mongoClient = null;
    private MongoDatabase mongodatabase = null;

    private Database(String ip, int port, String databaseName) {
        this.ip = ip;
        this.port = port;
        this.databaseName = databaseName;
        
        mongoClient = new MongoClient(ip, port);
        mongodatabase = mongoClient.getDatabase(databaseName);
    }
    
    public static Database getInstance(String ip, int port, String databaseName) {
        
        if(database != null) {
            throw new IllegalStateException("The database has already been initialized!");
        }
        
        database = new Database(ip, port, databaseName);
        
        return database;
    }
    
    public static Database getInstance() {
        
        if(database == null) {
            throw new IllegalStateException("The database has not been initialized yet!");
        }
        
        return database;
    }
    
    /**
     * Inserts a {@link Position position} into the
     * {@link MongoCollection collection} of ships positions.
     * @param position The position to insert.
     */
    public void insertPosition(Position position) {
        MongoCollection collection = mongodatabase.getCollection(collNameShipPosition);
        Document document = Document.parse(GSON.toJson(position));
        if (position.getId() == null) {
            position.setId(new ObjectId());
        }
        collection.insertOne(document);
    }
    
    public Position getLastShipPosition(String shipName) {
        MongoCollection coll = this.mongodatabase.getCollection(this.collNameShipPosition);
        Document doc = (Document) coll.find(
            Filters.eq("shipName", shipName)
        )
        .sort(Sorts.orderBy(Indexes.descending("datetime")))
        .first();
        
        return GSON.fromJson(doc.toJson(), Position.class);
    }
    
    public double getDistanceBetween(String nameShip, Position spPositionTarget) throws Exception {
        Position shPos = getLastShipPosition(nameShip); //to get oid of document
        // The final command object
        BasicDBObject distance = new BasicDBObject();
        // Represents the parameters of the command
        BasicDBObject distanceData = new BasicDBObject();
        // The origin location has to be provided
        // if Mongo-Position is given then
        // Double[] loc =
        //     shPos.getPosition().getPosition().getValues().toArray(
        //        new Double[0]);
        Double[] loc = {spPositionTarget.getPosition().getCoordinates().getValues().get(0), spPositionTarget.getPosition().getCoordinates().getValues().get(1)};
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
        AggregateIterable<Document> docs =
            mongodatabase.getCollection(collNameShipPosition)
                .aggregate(
                    Arrays.asList(
                        distance
                    )
                );
        double result;
        Document doc = docs.first();
        // Could be null so we stay on the safe side
        if(doc == null) {
            result = -1d;
        } else {
            result = docs.first().getDouble("__dist_calculated");
        }
        return result;
    }
}
