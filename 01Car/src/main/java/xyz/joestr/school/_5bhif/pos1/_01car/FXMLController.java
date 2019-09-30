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
import javafx.scene.text.Text;
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
    private Text text_label;

    @FXML
    void onActionMenuCarMenuItemDelete(ActionEvent event) {
        
    }

    @FXML
    void onActionMenuCarMenuItemFindAll(ActionEvent event) {
        cars.setAll(db.getAllCars());
        this.text_label.setText("Loaded all elements from database!");
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
        this.text_label.setText("Inserted " + c.toLongString());
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
        this.text_label.setText("Successfully connected to database!");
    }

    @FXML
    void onActionMenuDatabaseMenuItemCreateTextIndex(ActionEvent event) {

    }
    
    MongoClient mc;
    Database db;
    private ObservableList<Car> cars;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        cars = FXCollections.observableArrayList();
        this.listview_cars.setItems(cars);
    }
}
