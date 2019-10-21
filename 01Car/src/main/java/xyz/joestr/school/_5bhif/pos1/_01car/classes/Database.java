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
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.Collection;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author admin
 */
public class Database {

    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;
    private static String CONNECTIONSTRING;
    private static final String DATABASENAME = "mydb";
    private static final String CARCOLLECTION = "Cars";
    private static final String OWNERCOLLECTION = "Owners";

    private static Database database = null;
    private static MongoCollection<Document> carsCollection;
    private static MongoCollection<Document> ownersCollection;

    public static Database getInstance(String ip) throws Exception {
        if (database == null) {
            database = new Database();
            CONNECTIONSTRING = ip;
            mongoClient = new MongoClient(CONNECTIONSTRING, 27017);
            mongoDatabase = mongoClient.getDatabase(DATABASENAME);
            carsCollection = mongoDatabase.getCollection(CARCOLLECTION);
            ownersCollection = mongoDatabase.getCollection(OWNERCOLLECTION);
        }
        return database;
    }

    private Database() throws Exception {

    }

    public void close() throws Exception {
        mongoClient.close();
        database = null;
    }

    public void createTextIndex() throws Exception {
        //splits text in tokens; updated automatically
        carsCollection.createIndex(Indexes.text("description"));
    }

    /**
     * Gets all cars.
     * @return A collection of cars
     * @throws Exception If an error occours
     */
    public Collection<Car> getAllCars() throws Exception {
        Collection<Car> collCars = new ArrayList<>();
        Document sortOrder = new Document("year", -1); //desc
        sortOrder.append("name", 1); //asc
        for (Document doc : carsCollection.find().sort(sortOrder)) {
            Car car = new Gson().fromJson(doc.toJson(), Car.class);
            car.setId((ObjectId) doc.get("_id"));
            collCars.add(car);
        }
        return collCars;
    }

    /**
     * Gets all cars ordered by the relevance of the filter.
     * @param strFilter The filter
     * @return A list with cars
     * @throws Exception If an error occours
     */
    public ArrayList<Car> getAllCarsOrderedByRelevance(String strFilter) throws Exception {
        ArrayList<Car> collCars = new ArrayList<>();
        ArrayList<Document> collDocuments
                = carsCollection.find(new Document("$text",
                        new Document("$search", strFilter)
                                .append("$caseSensitive", false)
                                .append("$diacriticSensitive", false)))
                        .projection(Projections.metaTextScore("score"))
                        .sort(Sorts.metaTextScore("score"))
                        .into(new ArrayList<>());
        for (Document doc : collDocuments) {
            Car car = new Gson().fromJson(doc.toJson(), Car.class);
            car.setId((ObjectId) doc.get("_id"));
            collCars.add(car);
        }
        return collCars;
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
        car.setId((ObjectId) doc.get("_id"));
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
        UpdateResult ur = carsCollection.updateOne(eq("_id", newCar.getId()), new Document("$set", doc));
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
        //update owner's data in each possessed owner
        return ownersCollection.updateOne(
            eq("_id", Document.parse(new Gson().toJson(owner.getId()))),
            new Document("$set", Document.parse(new Gson().toJson(owner)))).getModifiedCount();
    }
    
    /**
     * Replaces the given owner.
     * @param owner The owner to replace
     * @return The modified count
     * @throws Exception If an error occours
     */
    public long replaceCar(Owner owner) throws Exception {
        Document doc = Document.parse(new Gson().toJson(owner));
        doc.remove("_id");
        UpdateResult ur = ownersCollection.replaceOne(eq("_id", owner.getId()), doc);
        return ur.getModifiedCount();
    }
    
    /**
     * Deletes a owner.
     * @param car The owner to delete
     * @return The modified count
     * @throws Exception If an error occours
     */
    public long deleteOwner(Owner owner) throws Exception {
        return ownersCollection.deleteOne(eq("_id", owner.getId())).getDeletedCount();
    }
    
    /**
     * Get all ownerships of cars.
     * @return List of ownerships
     * @throws Exception If an error occours
     */
    public Collection<Ownership> getAllOwnerships() throws Exception {
        ArrayList<Ownership> collOwnerships = new ArrayList<>();
        carsCollection.distinct("owner", Document.class)
                .into(new ArrayList<>())
                .forEach(doc -> collOwnerships.add(new Gson().fromJson(((Document) doc).toJson(), Ownership.class)));
        //mongo doesn't support both distinct and sort in one statement
        collOwnerships.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        return collOwnerships;
    }

    public ArrayList<Car> getCarsOfOwner(Owner owner) {
        ArrayList<Car> collCars = new ArrayList<>();
        ArrayList<Document> carDocs = (ArrayList<Document>) carsCollection
                .find(exists("owner", true))
                .filter(eq("owner._id", Document.parse(new Gson().toJson(owner.getId()))))
                .into(new ArrayList<>());
        for (Document doc : carDocs) {
            String jsonString = doc.toJson();
            Car car = new Gson().fromJson(jsonString, Car.class);
            car.setId((ObjectId) doc.get("_id"));
            this.setOwnerIdOfCar(car, doc);
            collCars.add(car);
        }
        return collCars;
    }

    public void assignOwnerToCar(Car car, Owner owner) throws Exception {
        car.setOwner(owner);
        this.replaceCar(car);
    }

    public long deleteOwnerFromCar(Car carWithOwner) {
        String jsonString = new Gson().toJson(carWithOwner);
        Document doc = Document.parse(jsonString);
        doc.remove("_id");
        UpdateResult ur = carsCollection.updateOne(eq("_id", carWithOwner.getId()), new Document("$unset", new Document("owner", "")));
        return ur.getModifiedCount();
    }

    private void setOwnerIdOfCar(Car car, Document doc) {
       Document ownerDoc = doc.get("owner", Document.class);
       ownerDoc.put("_id", car.getId());
   }
}
