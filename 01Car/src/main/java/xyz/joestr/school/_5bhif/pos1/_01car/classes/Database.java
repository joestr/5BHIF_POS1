/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.school._5bhif.pos1._01car.classes;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.bson.BsonDateTime;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author admin
 */
public class Database {

    private static final String DATABASENAME = "mydb";
    private static final String CARCOLLECTION = "Cars";
    private static final String OWNERCOLLECTION = "Owners";

    private static Database database = null;
    
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    private static MongoCollection<Document> carsCollection;
    private static MongoCollection<Document> ownersCollection;

    public static Database getInstance(String ip) throws Exception {
        if (database == null) {
            database = new Database();
            
            mongoClient = new MongoClient(ip, 27017);
            
            mongoDatabase = mongoClient.getDatabase(DATABASENAME);
            
            carsCollection = mongoDatabase.getCollection(CARCOLLECTION);
            ownersCollection = mongoDatabase.getCollection(OWNERCOLLECTION);
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

    /**
     * Creates necessary indizes for wider functionality.
     * @deprecated The name is misleading since this method creates multiple
     * indizes and not only text inidizes.
     * @throws Exception 
     */
    public void createTextIndex() throws Exception {
        carsCollection.createIndex(Indexes.text("description"));
        ownersCollection.createIndex(Indexes.text("details"));
    }

    /**
     * Gets all cars sorted by the year descending and name acending.
     * @return A collection of cars
     * @throws Exception If an error occours
     */
    public Collection<Car> getAllCars() throws Exception {
        Collection<Car> cars = new ArrayList<>();
        
        // The sorting order
        Document sortOrder = new Document("year", -1); // by year descending
        sortOrder.append("name", 1);                   // and name ascending
        
        for (Document doc : carsCollection.find().sort(sortOrder)) {
            Car car = new Gson().fromJson(doc.toJson(), Car.class);
            car.setId(doc.get("_id", ObjectId.class));
            cars.add(car);
        }
        
        return cars;
    }

    /**
     * Gets all cars ordered by the relevance of the filter.
     * @param strFilter The filter
     * @return A list with cars
     * @throws Exception If an error occours
     */
    public Collection<Car> getAllCarsOrderedByRelevance(String strFilter) throws Exception {
        Collection<Car> cars = new ArrayList<>();
        
        Collection<Document> docs
            = carsCollection.find(
                new Document("$text",
                    new Document("$search", strFilter)
                        .append("$caseSensitive", false)
                        .append("$diacriticSensitive", false)
                )
            )
            .projection(Projections.metaTextScore("score"))
            .sort(Sorts.metaTextScore("score"))
            .into(new ArrayList<>());
        
        for (Document doc : docs) {
            Car car = new Gson().fromJson(doc.toJson(), Car.class);
            car.setId(doc.get("_id", ObjectId.class));
            cars.add(car);
        }
        
        return cars;
    }

    /**
     * Inserts a new car.
     * @param car The car to insert
     * @return The object id of the inserted car
     * @throws Exception If an error occours
     */
    public ObjectId insertCar(Car car) throws Exception {
        Document doc = Document.parse(new Gson().toJson(car));
        carsCollection.insertOne(doc);
        car.setId(doc.get("_id", ObjectId.class));
        return doc.get("_id", ObjectId.class);
    }
    
    /**
     * Updated a car.
     * @param newCar The car to update
     * @return The modified count
     * @throws Exception If an error occours
     */
    public long updateCar(Car newCar) throws Exception {
        Document doc = Document.parse(new Gson().toJson(newCar));
        doc.remove("_id");
        UpdateResult ur = carsCollection.updateOne(
            Filters.eq("_id", newCar.getId()),
            new Document("$set", doc)
        );
        return ur.getModifiedCount();
    }

    /**
     * Replaces the given car.
     * @param car The owner to replace
     * @return The modified count
     * @throws Exception If an error occours
     */
    public long replaceCar(Car car) throws Exception {
        Document doc = Document.parse(new Gson().toJson(car));
        doc.remove("_id");
        UpdateResult ur = carsCollection.replaceOne(eq("_id", car.getId()), doc);
        return ur.getModifiedCount();
    }

    /**
     * Deletes a car.
     * @param car The car to delete
     * @return The modified count
     * @throws Exception If an error occours
     */
    public long deleteCar(Car car) throws Exception {
        return carsCollection.deleteOne(eq("_id", car.getId())).getDeletedCount();
    }

    /**
     * Gets all owners.
     * @return All owners
     * @throws Exception If an error occours
     */
    public Collection<Owner> getAllOwners() throws Exception {
        Collection<Owner> collowners = new ArrayList<>();
        Document sortOrder = new Document("year", -1); //desc
        sortOrder.append("name", 1); //asc
        for (Document doc : ownersCollection.find().sort(sortOrder)) {
            Owner owner = new Gson().fromJson(doc.toJson(), Owner.class);
            owner.setId((ObjectId) doc.get("_id"));
            collowners.add(owner);
        }
        return collowners;
    }
    
    /**
     * Gets all owners odered by search filters.
     * @param strFilter The filter
     * @return List of owners
     * @throws Exception If an error occours
     */
    public Collection<Owner> getAllOwnersOrderedByRelevance(String strFilter) throws Exception {
        ArrayList<Owner> collOwners = new ArrayList<>();
        ArrayList<Document> collDocuments
                = ownersCollection.find(new Document("$text",
                        new Document("$search", strFilter)
                                .append("$caseSensitive", false)
                                .append("$diacriticSensitive", false)))
                        .projection(Projections.metaTextScore("score"))
                        .sort(Sorts.metaTextScore("score"))
                        .into(new ArrayList<>());
        for (Document doc : collDocuments) {
            Owner owner = new Gson().fromJson(doc.toJson(), Owner.class);
            owner.setId((ObjectId) doc.get("_id"));
            collOwners.add(owner);
        }
        return collOwners;
    }
    
    /**
     * Inserts an owner.
     * @param owner THe owner to insert
     * @return The object id of the inserted owner
     * @throws Exception If an error occours
     */
    public ObjectId insertOwner(Owner owner) throws Exception {
        Document doc = Document.parse(new Gson().toJson(owner));
        doc.append(
            "birthISO",
            new BsonDateTime(
                owner.getBirth().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000
            )
        );
        ownersCollection.insertOne(doc);
        owner.setId((ObjectId) doc.get("_id"));
        return doc.get("_id", ObjectId.class);
    }
    
    /**
     * Updates an owner.
     * @param owner The owner to update
     * @return THe modified count
     * @throws Exception If an error occours
     */
    public long updateOwner(Owner owner) throws Exception {
        Document doc = Document.parse(new Gson().toJson(owner));
        doc.remove("_id");
        doc.append("birthISO",
                new BsonDateTime(owner.getBirth().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000));
        return ownersCollection.updateOne(eq("_id", owner.getId()), new Document("$set", doc)).getModifiedCount();
    }
    
    /**
     * Replaces the given owner.
     * @param owner The owner to replace
     * @return The modified count
     * @throws Exception If an error occours
     */
    public long replaceOwner(Owner owner) throws Exception {
        Document doc = Document.parse(new Gson().toJson(owner));
        doc.remove("_id");
        UpdateResult ur = ownersCollection.replaceOne(eq("_id", owner.getId()), doc);
        return ur.getModifiedCount();
    }
    
    /**
     * Deletes a owner.
     * @param owner The owner to delete
     * @return The modified count
     * @throws Exception If an error occours
     */
    public long deleteOwner(Owner owner) throws Exception {
        return ownersCollection.deleteOne(eq("_id", owner.getId())).getDeletedCount();
    }
    
    /**
     * Add the supplied ownership to the supplied car.
     * @param car The car to apply the ownership to
     * @param ownership The ownership applied to the car
     * @return The modifief car
     * @throws Exception 
     */
    public long addOwnershipToCar(Car car, Ownership ownership) throws Exception {
        
        long matches = carsCollection.countDocuments(Filters.and(
            Filters.eq("_id", car.getId()),
            Filters.eq("ownerships.startDateISO", new BsonDateTime(ownership.getStartDate().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000))
        ));
        
        if (matches > 0) {
            throw new IllegalStateException("Unique contraints violated: _id and ownerships.startDateISO must be unique in collection cars");
        }

        Document doc = new Document(); //only consists of special fields like ObjectId, ISODate
        doc.append("ownerId", ownership.getOwnerId());
        doc.append("startDateISO",
                new BsonDateTime(ownership.getStartDate().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000));
        if (ownership.getEndDate() != null) {
            doc.append("endDateISO",
                    new BsonDateTime(ownership.getEndDate().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000));
        }
        return carsCollection.updateOne(eq("_id", car.getId()), Updates.addToSet("ownerships", doc)).getModifiedCount();
    }

    /**
     * The all ownerhips of the supplied car
     * @param car The car to get the ownerships from
     * @return List of ownerships
     */
    public ArrayList<Ownership> getOwnershipsOfCar(Car car) {
        ArrayList<Ownership> ownerships = new ArrayList<>();
        Document first = carsCollection.find(eq("_id", car.getId())).projection(Projections.include("ownerships")).first();
        ArrayList<Document> listOfDocs = (ArrayList<Document>) first.get("ownerships");
        if(listOfDocs == null) return ownerships;
        for (Document doc : listOfDocs) {
            Ownership ownership = new Ownership();
            ownership.setOwnerId(doc.get("ownerId", ObjectId.class));
            ownership.setStartDate(doc.get("startDateISO", Date.class).toInstant().atZone(ZoneId.of("UTC")).toLocalDate());
            if (doc.get("endDateISO") != null) {
                ownership.setEndDate(doc.get("endDateISO", Date.class).toInstant().atZone(ZoneId.of("UTC")).toLocalDate());
            }
            ownerships.add(ownership);
        }
        return ownerships;
    }

    /**
     * Removes the supplied ownership from the supplied car.
     * @param car The car from which the supplied ownership will be removed
     * @param ownership The supplied ownerhsip
     * @return The modified count
     */
    public long removeOwnershipFromCar(Car car, Ownership ownership) {
        return carsCollection.updateOne(
            Filters.eq(
                "_id",
                car.getId()
            ),
            Updates.pull(
                "ownerships",
                Filters.and(
                    Filters.eq(
                        "ownerId",
                        ownership.getOwnerId()
                    ),
                    Filters.eq(
                        "startDateISO",
                        new BsonDateTime(ownership.getStartDate().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000)
                    )
                )
            )
        ).getModifiedCount();
    }
    
    /**
     * Gets all cars of the selected owner.
     * @param owner The owner fro which we want all cars
     * @return List of cars
     */
    public ArrayList<Car> getCarsOfOwner(Owner owner) {
        ArrayList<Car> collCars = new ArrayList<>();
        //https://docs.mongodb.com/manual/reference/operator/query/elemMatch/
        carsCollection.find(
            Filters.elemMatch(
                "ownerships",
                Filters.eq(
                    "ownerId", owner.getId()
                )
            )
        )
        .into(new ArrayList<>())
        .forEach(doc -> collCars.add(convertDocToCarWizard(doc)));
        return collCars;
    }
    

    private Owner convertDocToOwner(Document doc) throws JsonSyntaxException {
        Date birthDate = doc.get("birthISO", Date.class);
        Owner owner = new Gson().fromJson(((Document) doc).toJson(), Owner.class);
        owner.setBirth(birthDate);
        owner.setId((ObjectId) doc.get("_id")); //gemein
        return owner;
    }

    private Car convertDocToCarWizard(Document doc) throws JsonSyntaxException {
        String jsonString = doc.toJson();
        Car car = new Gson().fromJson(jsonString, Car.class);
        car.setId((ObjectId) doc.get("_id"));
        return car;
    }
    
    public Owner getOwnerById(ObjectId idOwner) {
        return convertDocToOwner(ownersCollection.find(eq("_id", idOwner)).first());
    }
}
