package pkg01cargeo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class FXMLDocumentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label label;

    @FXML
    private MenuItem menuitem_database_initialize;

    @FXML
    private MenuItem menuitem_database_createspatialindex;

    @FXML
    private MenuItem menuitem_database_readcarsandpetrolstations;

    @FXML
    private MenuItem menuitem_database_petrolstationsinneighborhood;

    @FXML
    private ListView<Car> listview_car_cars;

    @FXML
    private ListView<PetrolStation> listview_petrolstation_petrolstations;

    @FXML
    private Label label_logger;

    @FXML
    void onActionButton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert menuitem_database_initialize != null : "fx:id=\"menuitem_database_initialize\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert menuitem_database_createspatialindex != null : "fx:id=\"menuitem_database_createspatialindex\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert menuitem_database_readcarsandpetrolstations != null : "fx:id=\"menuitem_database_readcarsandpetrolstations\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert menuitem_database_petrolstationsinneighborhood != null : "fx:id=\"menuitem_database_petrolstationsinneighborhood\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert listview_car_cars != null : "fx:id=\"listview_car_cars\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert listview_petrolstation_petrolstations != null : "fx:id=\"listview_petrolstation_petrolstations\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert label_logger != null : "fx:id=\"label_logger\" was not injected: check your FXML file 'FXMLDocument.fxml'.";

    }
}
