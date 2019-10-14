/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package xyz.joestr.school._5bhif.pos1._01car;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.bson.types.ObjectId;
import xyz.joestr.school._5bhif.pos1._01car.classes.Car;
import xyz.joestr.school._5bhif.pos1._01car.classes.Database;
import xyz.joestr.school._5bhif.pos1._01car.classes.Owner;

public class FXMLController {
    

    @FXML
    private Label label;

    @FXML
    private MenuItem menu_database_menuitem_connect;

    @FXML
    private MenuItem menu_database_menuitem_close;

    @FXML
    private MenuItem menu_database_menuitem_createtextindex;

    @FXML
    private MenuItem menu_car_menuitem_insert;

    @FXML
    private MenuItem menu_car_menuitem_delete;

    @FXML
    private MenuItem menu_car_menuitem_update;

    @FXML
    private MenuItem menu_car_menuitem_replace;

    @FXML
    private MenuItem menu_car_menuitem_findall;

    @FXML
    private MenuItem menu_car_menuitem_findrelevance;

    @FXML
    private MenuItem menu_owner_menuitem_add;

    @FXML
    private MenuItem menu_owner_menuitem_update;

    @FXML
    private MenuItem menu_owner_menuitem_delete;

    @FXML
    private MenuItem menu_owner_menuitem_replace;

    @FXML
    private MenuItem menu_owner_menuitem_findall;

    @FXML
    private TextField textfield_database_ip;

    @FXML
    private TextField textfield_car_name;

    @FXML
    private TextField textfield_car_productionyear;

    @FXML
    private TextField textfield_car_kw;

    @FXML
    private ListView<Car> listview_cars;

    @FXML
    private TextArea textarea_car_description;

    @FXML
    private Text text_label;

    @FXML
    private TextField textfield_search;

    @FXML
    private TextField owner_name;

    @FXML
    private TextArea owner_details;

    @FXML
    private DatePicker owner_birth;

    @FXML
    private ListView<Owner> listview_owners;

    @FXML
    void onActionMenuCarMenuItemDelete(ActionEvent event) {
        
    }

    @FXML
    void onActionMenuCarMenuItemFindAll(ActionEvent event) throws Exception {
        cars.setAll(db.getAllCars());
        this.text_label.setText("Loaded all elements from database");
    }

    @FXML
    void onActionMenuCarMenuItemFindRelevance(ActionEvent event) {
        try {
            cars.setAll(db.getAllCarsOrderedByRelevance(this.textfield_search.getText()));
        } catch (Exception ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.text_label.setText("Loaded all elemnts by relevance: '" + this.textfield_search.getText() + "'");
    }

    @FXML
    void onActionMenuCarMenuItemInsert(ActionEvent event) throws Exception {
        Car c = new Car(
            textfield_car_name.getText(),
            Integer.parseInt(textfield_car_kw.getText()),
            Integer.parseInt(textfield_car_productionyear.getText()),
            textarea_car_description.getText()
        );
        db.insertCar(c);
        this.text_label.setText("Inserted car: '" + c.toLongString() + "'");
    }

    @FXML
    void onActionMenuCarMenuItemReplace(ActionEvent event) throws Exception {
        Car c = new Car(
            textfield_car_name.getText(),
            Integer.parseInt(textfield_car_kw.getText()),
            Integer.parseInt(textfield_car_productionyear.getText()),
            textarea_car_description.getText()
        );
        db.replaceCar(c);
    }

    @FXML
    void onActionMenuCarMenuItemUpdate(ActionEvent event) throws Exception {
        Car c = new Car(
            textfield_car_name.getText(),
            Integer.parseInt(textfield_car_kw.getText()),
            Integer.parseInt(textfield_car_productionyear.getText()),
            textarea_car_description.getText()
        );
        c.setId(selectedCar.getId());
        db.updateCar(c);
    }

    @FXML
    void onActionMenuDatabaseMenuItemConnect(ActionEvent event) throws Exception {
        db = Database.getInstance(textfield_database_ip.getText());
        this.text_label.setText("Successfully connected to database!");
    }

    @FXML
    void onActionMenuDatabaseMenuItemCreateTextIndex(ActionEvent event) {

    }
    
    @FXML
    void onActionMenuOwner(ActionEvent event) throws Exception {

        // Initial insert to the car
        if(event.getSource().equals(this.menu_owner_menuitem_add)) {
            Owner o = new Owner(
                this.owner_name.getText(),
                this.owner_details.getText(),
                this.owner_birth.getValue()
            );
            
            db.insertOwner(selectedCar, o);
        }
        
        if(event.getSource().equals(this.menu_owner_menuitem_delete)) {
            
            this.selectedCar.setOwner(null);
            
            try {
                db.updateCar(this.selectedCar);
            } catch (Exception ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(event.getSource().equals(this.menu_owner_menuitem_update)) {
            
            Owner o = this.selectedCar.getOwner();
            
            o.setName(this.owner_name.getText());
            o.setDetails(this.owner_details.getText());
            o.setBirth(this.owner_birth.getValue());
            
            this.selectedCar.setOwner(o);
            
            db.updateCar(this.selectedCar);
        }
        
        if(event.getSource().equals(this.menu_owner_menuitem_replace)) {
            Owner o = this.selectedCar.getOwner();
            
            o.setName(this.owner_name.getText());
            o.setDetails(this.owner_details.getText());
            o.setBirth(this.owner_birth.getValue());
            
            o.setId(selectedOwner.getId());
            
            db.replaceOwner(o);
        }
        
        if(event.getSource().equals(this.menu_owner_menuitem_findall)) {
            
            this.owners.setAll(db.getAllOwners());
        }
    }
    
    MongoClient mc;
    Database db;
    private ObservableList<Car> cars;
    private ObservableList<Owner> owners;
    Car selectedCar;
    Owner selectedOwner;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        cars = FXCollections.observableArrayList();
        owners = FXCollections.observableArrayList();
        this.listview_cars.setItems(cars);
        this.listview_owners.setItems(owners);
        
        this.listview_cars.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Car> observable, Car oldValue, Car newValue) -> {
            if (newValue != null) {
                selectedCar = newValue;
                textfield_car_name.setText(selectedCar.getName());
                textfield_car_productionyear.setText("" + selectedCar.getYear());
                textfield_car_kw.setText("" + selectedCar.getHp());
                
                if (this.selectedCar.getOwner() != null) {
                    this.owners.setAll(db.getOwnerOfCar(selectedCar));
                }
            }
        });
        
        this.listview_owners.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Owner> observable, Owner oldValue, Owner newValue) -> {
            if (newValue != null) {
                selectedOwner = newValue;
                owner_details.setText(selectedOwner.getDetails());
                owner_name.setText(selectedOwner.getName());
                owner_birth.setValue(selectedOwner.getBirth());
            }
            
            this.cars.setAll(db.getCarsOfOwner(selectedOwner));
        });
    }
    
    public void weDoVOidHereSinceJavaDoesNotProvideAnyVoidCall() {
        
    };
}
