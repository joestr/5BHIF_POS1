/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.joestr.school._5bhif.pos1._01car.classes;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
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
public class Database2 {

    private static String CONNECTIONSTRING;
    private static final String COLLECTIONNAME_CARS = "Cars";
    private static final String COLLECTIONNAME_OWNERS = "Owners";
    private static final String DATABASENAME = "mydb2";
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    private static Database2 database = null;
    private static MongoCollection<Document> carsCollection;
    private static MongoCollection<Document> ownersCollection;

    public static Database2 getInstance(String ip) throws Exception {
        if (database == null) {
            database = new Database2();
            CONNECTIONSTRING = ip;
            mongoClient = new MongoClient(CONNECTIONSTRING, 27017);
            mongoDatabase = mongoClient.getDatabase(DATABASENAME);
            carsCollection = mongoDatabase.getCollection(COLLECTIONNAME_CARS);
            ownersCollection = mongoDatabase.getCollection(COLLECTIONNAME_OWNERS);
        }
        return database;
    }

    private Database2() throws Exception {

    }

    public void close() throws Exception {
        mongoClient.close();
        database = null;
    }

    public void createTextIndex() throws Exception {
        //splits text in tokens; updated automatically
        carsCollection.createIndex(Indexes.text("description"));
    }
/*
//<editor-fold defaultstate="collapsed" desc="car operations">
    public Collection<Car> getAllCars() throws Exception {
        Collection<Car> collCars = new ArrayList<>();
        Document sortOrder = new Document("year", -1); //desc
        sortOrder.append("name", 1); //asc
        carsCollection.find().sort(sortOrder).into(new ArrayList<>()).forEach(doc -> collCars.add(convertDocToCarWizard(doc)));
        return collCars;
    }

    public ArrayList<Car> getAllCarsOrderedByRelevance(String strFilter) throws Exception {
        ArrayList<Car> collCars = new ArrayList<>();
        carsCollection.find(new Document("$text",
                new Document("$search", strFilter)
                        .append("$caseSensitive", false)
                        .append("$diacriticSensitive", false)))
                .projection(Projections.metaTextScore("score"))
                .sort(Sorts.metaTextScore("score"))
                .into(new ArrayList<>())
                .forEach(doc -> collCars.add(convertDocToCarWizard(doc)));
        return collCars;
    }

    public ObjectId insertCar(Car car) throws Exception {
        Document doc = Document.parse(new Gson().toJson(car));
        carsCollection.insertOne(doc);
        car.setId((ObjectId) doc.get("_id"));
        return (ObjectId) doc.get("_id");
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

    public long deleteCar(Car car) throws Exception {
        return carsCollection.deleteOne(eq("_id", car.getId())).getDeletedCount();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="owner operations">
    public ArrayList<Owner> getAllOwners() throws Exception {
        ArrayList<Owner> collOwners = new ArrayList<>();
        ownersCollection.find()
                .sort(new Document("name", 1))
                .into(new ArrayList<>())
                .forEach(doc -> collOwners.add(convertDocToOwner(doc)));
        return collOwners;
    }

    public Owner getOwnerById(ObjectId idOwner) {
        return convertDocToOwner(ownersCollection.find(eq("_id", idOwner)).first());
    }

    public ArrayList<Car> getCarsOfOwner(Owner owner) {
        ArrayList<Car> collCars = new ArrayList<>();
        //https://docs.mongodb.com/manual/reference/operator/query/elemMatch/
        carsCollection.find(Filters.elemMatch("collOwnership", Filters.eq("idOwner", owner.getId())))
                .into(new ArrayList<>())
                .forEach(doc -> collCars.add(convertDocToCarWizard(doc)));
        return collCars;
    }

    public ObjectId insertOwner(Owner owner) throws Exception {
        Document doc = Document.parse(new Gson().toJson(owner));
        doc.append("birthISO",
                new BsonDateTime(owner.getBirth().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000));
        ownersCollection.insertOne(doc);
        owner.setId((ObjectId) doc.get("_id"));
        return (ObjectId) doc.get("_id");
    }

    public long updateOwner(Owner updatedOwner) throws Exception {
        Document doc = Document.parse(new Gson().toJson(updatedOwner));
        doc.remove("_id");
        doc.append("birthISO",
                new BsonDateTime(updatedOwner.getBirth().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000));
        return ownersCollection.updateOne(eq("_id", updatedOwner.getId()), new Document("$set", doc)).getModifiedCount();
    }

    public long replaceOwner(Owner owner) throws Exception {
        return ownersCollection.replaceOne(eq("owner._id", Document.parse(new Gson().toJson(owner.getId()))), Document.parse(new Gson().toJson(owner))).getModifiedCount();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="ownership">
    public long addOwnershipToCar(Car car, Ownership ownership) throws Exception {
        //car and date must be unique
        long matches = carsCollection.countDocuments(Filters.and(
                Filters.eq("_id", car.getId()),
//                Filters.eq("collOwnership._ownerId", ownership.getIdOwner()),
                Filters.eq("collOwnership.startDateISO", new BsonDateTime(ownership.getStartDate().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000))
        ));
        if (matches > 0) {
            throw new Exception("unique constraint violated: owner id and start date must be unique");
        }

        Document doc = new Document(); //only consists of special fields like ObjectId, ISODate
        doc.append("idOwner", ownership.getIdOwner());
        doc.append("startDateISO",
                new BsonDateTime(ownership.getStartDate().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000));
        if (ownership.getEndDate() != null) {
            doc.append("endDateISO",
                    new BsonDateTime(ownership.getEndDate().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000));
        }
        return carsCollection.updateOne(eq("_id", car.getId()), Updates.addToSet("collOwnership", doc)).getModifiedCount();
    }

    public ArrayList<Ownership> getOwnershipsOfCar(Car car) {
        ArrayList<Ownership> ownerships = new ArrayList<>();
        Document first = carsCollection.find(eq("_id", car.getId())).projection(Projections.include("collOwnership")).first();
        ArrayList<Document> listOfDocs = (ArrayList<Document>) first.get("collOwnership");
        for (Document doc : listOfDocs) {
            Ownership ownership = new Ownership();
            ownership.setIdOwner(doc.get("idOwner", ObjectId.class));
            ownership.setStartDate(doc.get("startDateISO", Date.class).toInstant().atZone(ZoneId.of("UTC")).toLocalDate());
            if (doc.get("endDateISO") != null) {
                ownership.setEndDate(doc.get("endDateISO", Date.class).toInstant().atZone(ZoneId.of("UTC")).toLocalDate());
            }
            ownerships.add(ownership);
        }
        return ownerships;
    }

    public long removeOwnershipFromCar(Car car, Ownership ownership) {
        return carsCollection.updateOne(
                eq("_id", car.getId()),
                Updates.pull("collOwnership", Filters.and(
                        eq("idOwner", ownership.getIdOwner()),
                        eq("startDateISO", new BsonDateTime(ownership.getStartDate().atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() * 1000))))).getModifiedCount();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="converters">
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
//</editor-fold>
*/
}