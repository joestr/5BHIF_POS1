/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01cargeo.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import java.util.ArrayList;
import org.bson.Document;
import pkg01cargeo.classes.Car;
import pkg01cargeo.classes.PetrolStation;
import com.mongodb.client.model.geojson.Position;
import com.mongodb.client.model.geojson.Point;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import org.bson.types.ObjectId;
import pkg01cargeo.classes.FuelType;
import pkg01cargeo.classes.GeoPosition;
import pkg01cargeo.serialzers.LocalDateTimeSerializer;
import pkg01cargeo.serialzers.ObjectIdSerializer;
import pkg01cargeo.serialzers.PointSerializer;

/**
 *
 * @author admin
 */
public class Database {

    private static final Gson GSON =
        new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
            .registerTypeAdapter(ObjectId.class, new ObjectIdSerializer())
            .registerTypeAdapter(Point.class, new PointSerializer())
            .create();
    
    private static final String DATABASENAME = "db_03CarGeo";
    private static final String COLLECTION_CARS = "Cars";
    private static final String COLLECTION_PETROLSTATIONS = "PetrolStations";

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
            
            carsCollection = mongoDatabase.getCollection(COLLECTION_CARS);
            petrolStationsCollection = mongoDatabase.getCollection(COLLECTION_PETROLSTATIONS);
            
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

    public void generateData() {
        collCars.clear();
        collCars.add(
            new Car(
                "Isetta Völkermarkt",
                FuelType.PETROL,
                new GeoPosition(
                    new Point(
                        new Position(14.634444, 46.662222)
                    ),
                    LocalDateTime.of(1990, Month.MARCH, 31, 7, 45)
                )
            )
        );
        
        collCars.add(
            new Car(
                "Suzuki Radenthein",
                FuelType.PETROL,
                new GeoPosition(
                    new Point(
                        new Position(13.7, 46.8)
                    ),
                    LocalDateTime.of(2019, Month.NOVEMBER, 6, 16, 50)
                )
            )
        );
        
        collCars.add(
            new Car(
                "DKW Udine",
                FuelType.DIESEL,
                new GeoPosition(
                    new Point(
                        new Position(13.233333, 46.066667)
                    ),
                    LocalDateTime.now()
                )
            )
        );
        
        collCars.add(
            new Car(
                "Rover Lienz",
                FuelType.DIESEL,
                new GeoPosition(
                    new Point(
                        new Position(12.769722, 46.829722)
                    ),
                    LocalDateTime.of(1999, Month.DECEMBER, 31, 23, 45)
                )
            )
        );
        
        collCars.add(
            new Car(
                "Tesla Murau",
                FuelType.ELECTRIC,
                new GeoPosition(
                    new Point(
                        new Position(14.173056, 47.111944)
                    ),
                    LocalDateTime.of(2018, Month.JULY, 26, 7, 9)
                )
            )
        );
        
        collPetrolStations.clear();
        
        collPetrolStations.add(
            new PetrolStation(
                "Esso Villach",
                Arrays.asList(FuelType.DIESEL, FuelType.PETROL),
                new Point(
                    new Position(13.846111, 46.614722)
                )
            )
        );
        
        collPetrolStations.add(
            new PetrolStation(
                "Tesla Supercharger Villach",
                Arrays.asList(FuelType.ELECTRIC),
                new Point(
                    new Position(13.846111, 46.614722)
                )
            )
        );
        
        collPetrolStations.add(
            new PetrolStation(
                "BP Afritz",
                Arrays.asList(FuelType.DIESEL, FuelType.PETROL, FuelType.ELECTRIC),
                new Point(
                    new Position(13.8, 46.7275)
                )
            )
        );
        
        collPetrolStations.add(
            new PetrolStation(
                "Avanti Klagenfurt",
                Arrays.asList(FuelType.DIESEL, FuelType.ELECTRIC),
                new Point(
                    new Position(14.305556, 46.617778)
                )
            )
        );
        
        collPetrolStations.add(
            new PetrolStation(
                "LH Spittal",
                Arrays.asList(FuelType.DIESEL, FuelType.PETROL),
                new Point(
                    new Position(13.495833, 46.791667)
                )
            )
        );
    }

    public void createSpatialIndex() {
        
        MongoCollection collection = mongoDatabase.getCollection(COLLECTION_CARS);
        if(collection != null) collection.drop();
        mongoDatabase.createCollection(COLLECTION_CARS);
        collection = mongoDatabase.getCollection(COLLECTION_CARS);
        collection.createIndex(Indexes.geo2dsphere("position.location"));
        
        collection = mongoDatabase.getCollection(COLLECTION_PETROLSTATIONS);
        if(collection != null) collection.drop();
        mongoDatabase.createCollection(COLLECTION_PETROLSTATIONS);
        collection = mongoDatabase.getCollection(COLLECTION_PETROLSTATIONS);
        collection.createIndex(Indexes.geo2dsphere("position"));
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

    /**
     * Gets all {@link Car cars} from the database.
     * @return A {@link ArrayList list} of cars
     */
    public ArrayList<Car> getAllCars() {
        ArrayList<Car> result = new ArrayList<>();
        
        MongoCollection<Document> c =
            mongoDatabase.getCollection(COLLECTION_CARS);
        
        FindIterable<Document> cF = c.find();
        
        for(Document d : cF) {
            result.add(
                GSON.fromJson(d.toJson(), Car.class)
            );
        }
        
        return result;
    }
    
    /**
     * Gets all {@link PetrolStation petrol stations} from the database.
     * @return A {@link ArrayList list} of petrol stations
     */
    public ArrayList<PetrolStation> getAllPetrolStations() {
        ArrayList<PetrolStation> result = new ArrayList<>();
        
        MongoCollection<Document> c =
            mongoDatabase.getCollection(COLLECTION_PETROLSTATIONS);
        
        FindIterable<Document> cF = c.find();
        
        for(Document d : cF) {
            result.add(
                GSON.fromJson(d.toJson(), PetrolStation.class)
            );
        }
        
        return result;
    }
    
    /**
     * Gets all {@link PetrolStation petrol stations} nearby the given
     * {@link Car car} and distance.
     * @param car The car
     * @param distance The maximum distance to search for a petrol station.
     * @return A {@link ArrayList list} of petrol stations
     */
    public ArrayList<PetrolStation> getAllPetrolStationsNearbyCar(Car car, double distance) {
        ArrayList<PetrolStation> result = new ArrayList<>();
        
        MongoCollection<Document> c =
            mongoDatabase.getCollection(COLLECTION_PETROLSTATIONS);
        
        FindIterable<Document> fC = c.find(
            Filters.and(
                Filters.near(
                    "position",
                    car.getPosition().getLocation(),
                    distance,
                    0d
                ),
                Filters.in("providedFuelTypes", car.getFuelType().toString())
            )
        );
        
        for(Document d : fC) {
            result.add(
                GSON.fromJson(d.toJson(), PetrolStation.class)
            );
        }
        
        return result;
    }
    
    /**
     * Calculates the distance (in kilometers) from a car to a specific petrol
     * station.
     * @param car The car
     * @param petrolStation The petrol station
     * @return The distance in kilometers
     */
    public double distanceBetween(Car car, PetrolStation petrolStation) {
        
        // The final command object
        BasicDBObject distance = new BasicDBObject();
        
        // Represents the parameters of the command
        BasicDBObject distanceData = new BasicDBObject();
        
        // The origin location has to be provided
        Double[] loc =
            car.getPosition().getLocation().getPosition().getValues().toArray(
                new Double[0]
            );
        distanceData.append("near", loc);
        
        // We want to use a sphere
        distanceData.append("spherical", true);
        
        // With "query" we can limit the geospatial query to specific docs
        distanceData.append(
            "query",
            Filters.eq("_id", petrolStation.getOid())
        );
        
        // The maximal distance in meters
        distanceData.append("maxDistance", 15000000);
        
        // Where the calculated distance will be available
        distanceData.append("distanceField", "__dist_calculated");
        
        // To get kilometers back (Why 6379.137? Earths radius.)
        distanceData.append("distanceMultiplier", 6378.137);
        
        // Chain the parameters to the command
        distance.append("$geoNear", distanceData);
        
        AggregateIterable<Document> docs =
            mongoDatabase.getCollection(COLLECTION_PETROLSTATIONS)
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
