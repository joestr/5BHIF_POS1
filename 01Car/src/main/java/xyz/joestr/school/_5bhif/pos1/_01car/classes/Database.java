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

    private static String CONNECTIONSTRING;
    private static final String COLLECTIONNAME = "Cars";
    private static final String DATABASENAME = "mydb";
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    private static Database database = null;
    private static MongoCollection<Document> carsCollection;

    public static Database getInstance(String ip) throws Exception {
        if (database == null) {
            database = new Database();
            CONNECTIONSTRING = ip;
            mongoClient = new MongoClient(CONNECTIONSTRING, 27017);
            mongoDatabase = mongoClient.getDatabase(DATABASENAME);
            carsCollection = mongoDatabase.getCollection(COLLECTIONNAME);
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

    //<editor-fold defaultstate="collapsed" desc="car operations">
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

    public long updateCar(Car newCar) throws Exception {
        Document doc = Document.parse(new Gson().toJson(newCar));
        doc.remove("_id");
        UpdateResult ur = carsCollection.updateOne(eq("_id", newCar.getId()), new Document("$set", doc));
        return ur.getModifiedCount();
    }

    public long replaceCar(Car newCar) throws Exception {
        Document doc = Document.parse(new Gson().toJson(newCar));
        doc.remove("_id");
        UpdateResult ur = carsCollection.replaceOne(eq("_id", newCar.getId()), doc);
        return ur.getModifiedCount();
    }

    public ObjectId insertCar(Car car) throws Exception {
        Document doc = Document.parse(new Gson().toJson(car));
        carsCollection.insertOne(doc);
        car.setId((ObjectId) doc.get("_id"));
        return (ObjectId) doc.get("_id");
    }

    public long deleteCar(Car car) throws Exception {
        return carsCollection.deleteOne(eq("_id", car.getId())).getDeletedCount();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="owner operations">
    public ArrayList<Owner> getAllOwners() throws Exception {
        ArrayList<Owner> collOwners = new ArrayList<>();
        carsCollection.distinct("owner", Document.class)
                .into(new ArrayList<>())
                .forEach(doc -> collOwners.add(new Gson().fromJson(((Document) doc).toJson(), Owner.class)));
        //mongo doesn't support both distinct and sort in one statement
        collOwners.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        return collOwners;
    }

    public Owner getOwnerOfCar(Car car) {
        return new Gson().fromJson(((Document) ((Document) carsCollection.find(eq("_id", car.getId())).first()).get("owner")).toJson(), Owner.class);
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
            collCars.add(car);
        }
        return collCars;
    }

    public void insertOwner(Car car, Owner owner) throws Exception {
        if (owner.getId() == null) {
            owner.setId(new ObjectId());
        }
        car.setOwner(owner);
        this.replaceCar(car);
    }

    public long updateOwner(Owner updateOwner) throws Exception {
        //update owner's data in each possessed car
        return carsCollection.updateMany(eq("owner._id", Document.parse(new Gson().toJson(updateOwner.getId()))), new Document("$set", new Document("owner", Document.parse(new Gson().toJson(updateOwner))))).getModifiedCount();
    }

    //not useful by now
    public long replaceOwner(Owner owner) throws Exception {
        return this.updateOwner(owner);
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
//</editor-fold>

    
    private void setOwnerIdOfCar(Car car, Document doc) {
       Document ownerDoc = doc.get("owner", Document.class);
       ownerDoc.put("_id", car.getId());
   }
}
