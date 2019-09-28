/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package xyz.joestr.school._5bhif.pos1._01car;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.bson.types.ObjectId;
import xyz.joestr.school._5bhif.pos1._01car.classes.Car;
import xyz.joestr.school._5bhif.pos1._01car.classes.Database;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="label"
    private Label label; // Value injected by FXMLLoader

    @FXML // fx:id="menu_car_menuitem_insert"
    private MenuItem menu_car_menuitem_insert; // Value injected by FXMLLoader

    @FXML // fx:id="menu_car_menuitem_delete"
    private MenuItem menu_car_menuitem_delete; // Value injected by FXMLLoader

    @FXML // fx:id="menu_car_menuitem_update"
    private MenuItem menu_car_menuitem_update; // Value injected by FXMLLoader

    @FXML // fx:id="menu_car_menuitem_replace"
    private MenuItem menu_car_menuitem_replace; // Value injected by FXMLLoader

    @FXML // fx:id="menu_car_menuitem_findall"
    private MenuItem menu_car_menuitem_findall; // Value injected by FXMLLoader

    @FXML // fx:id="menu_car_menuitem_findrelevance"
    private MenuItem menu_car_menuitem_findrelevance; // Value injected by FXMLLoader

    @FXML // fx:id="menu_database_menuitem_connect"
    private MenuItem menu_database_menuitem_connect; // Value injected by FXMLLoader

    @FXML // fx:id="menu_database_menuitem_close"
    private MenuItem menu_database_menuitem_close; // Value injected by FXMLLoader

    @FXML // fx:id="menu_database_menuitem_createtextindex"
    private MenuItem menu_database_menuitem_createtextindex; // Value injected by FXMLLoader

    @FXML // fx:id="textfield_database_ip"
    private TextField textfield_database_ip; // Value injected by FXMLLoader

    @FXML // fx:id="textfield_car_name"
    private TextField textfield_car_name; // Value injected by FXMLLoader

    @FXML // fx:id="textfield_car_productionyear"
    private TextField textfield_car_productionyear; // Value injected by FXMLLoader

    @FXML // fx:id="textfield_car_kw"
    private TextField textfield_car_kw; // Value injected by FXMLLoader
    
    @FXML // fx:id="textarea_car_description"
    private TextArea textarea_car_description; // Value injected by FXMLLoader
    
    @FXML // fx:id="listview_cars"
    private ListView<Car> listview_cars; // Value injected by FXMLLoader

    @FXML
    void onActionMenuCarMenuItemDelete(ActionEvent event) {
        
    }

    @FXML
    void onActionMenuCarMenuItemFindAll(ActionEvent event) {
        cars.setAll(db.getAllCars());
        
    }

    @FXML
    void onActionMenuCarMenuItemFindRelevance(ActionEvent event) {

    }

    @FXML
    void onActionMenuCarMenuItemInsert(ActionEvent event) {
        Car c = new Car(
            textfield_car_name.getText(),
            Integer.parseInt(textfield_car_productionyear.getText()),
            Integer.parseInt(textfield_car_kw.getText()),
            textarea_car_description.getText()
        );
        db.insertCart(c);
    }

    @FXML
    void onActionMenuCarMenuItemReplace(ActionEvent event) {

    }

    @FXML
    void onActionMenuCarMenuItemUpdate(ActionEvent event) {
        //db.updateCar(oldCar, newCar);
    }

    @FXML
    void onActionMenuDatabaseMenuItemConnect(ActionEvent event) throws Exception {
        db = Database.getInstance(textfield_database_ip.getText());
    }

    @FXML
    void onActionMenuDatabaseMenuItemCreateTextIndex(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'Scene.fxml'.";
        assert menu_car_menuitem_insert != null : "fx:id=\"menu_car_menuitem_insert\" was not injected: check your FXML file 'Scene.fxml'.";
        assert menu_car_menuitem_delete != null : "fx:id=\"menu_car_menuitem_delete\" was not injected: check your FXML file 'Scene.fxml'.";
        assert menu_car_menuitem_update != null : "fx:id=\"menu_car_menuitem_update\" was not injected: check your FXML file 'Scene.fxml'.";
        assert menu_car_menuitem_replace != null : "fx:id=\"menu_car_menuitem_replace\" was not injected: check your FXML file 'Scene.fxml'.";
        assert menu_car_menuitem_findall != null : "fx:id=\"menu_car_menuitem_findall\" was not injected: check your FXML file 'Scene.fxml'.";
        assert menu_car_menuitem_findrelevance != null : "fx:id=\"menu_car_menuitem_findrelevance\" was not injected: check your FXML file 'Scene.fxml'.";
        assert menu_database_menuitem_connect != null : "fx:id=\"menu_database_menuitem_connect\" was not injected: check your FXML file 'Scene.fxml'.";
        assert menu_database_menuitem_close != null : "fx:id=\"menu_database_menuitem_close\" was not injected: check your FXML file 'Scene.fxml'.";
        assert menu_database_menuitem_createtextindex != null : "fx:id=\"menu_database_menuitem_createtextindex\" was not injected: check your FXML file 'Scene.fxml'.";
        assert textfield_database_ip != null : "fx:id=\"textfield_database_ip\" was not injected: check your FXML file 'Scene.fxml'.";
        assert textfield_car_name != null : "fx:id=\"textfield_car_name\" was not injected: check your FXML file 'Scene.fxml'.";
        assert textfield_car_productionyear != null : "fx:id=\"textfield_car_productionyear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert textfield_car_kw != null : "fx:id=\"textfield_car_kw\" was not injected: check your FXML file 'Scene.fxml'.";
        assert listview_cars != null : "fx:id=\"listview_cars\" was not injected: check your FXML file 'Scene.fxml'.";
        assert textarea_car_description != null : "fx:id=\"textarea_car_description\" was not injected: check your FXML file 'Scene.fxml'.";
        
        this.listview_cars = new ListView<>(cars);
    }
    
    MongoClient mc;
    Database db;
    private ObservableList<Car> cars = FXCollections
      .observableArrayList();
}
