package pkg01cargeo;

import pkg01cargeo.utils.Database;
import pkg01cargeo.classes.Car;
import pkg01cargeo.classes.PetrolStation;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class FXMLDocumentController {

    public static final Logger LOGGER = Logger.getLogger("01CarGeo");
    public Database db = null;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem menuitem_database_createspatialindex;

    @FXML
    private MenuItem menuitem_database_insertdata;

    @FXML
    private MenuItem menuitem_operations_loadallcarsandpetrolstations;

    @FXML
    private MenuItem menuitem_operations_petrolstationsinneighborhood;
    
    @FXML
    private MenuItem menuitem_help_about;

    @FXML
    private ListView<Car> listview_car_cars;

    @FXML
    private ListView<PetrolStation> listview_petrolstation_petrolstations;

    @FXML
    private Label label_logger;
    
    @FXML
    private TextField textfield_distance;

    @FXML
    void onActionButton(ActionEvent event) {        
        if(event.getSource().equals(this.menuitem_database_createspatialindex)) {
            db.createSpatialIndex();
            LOGGER.log(Level.INFO, "Created Spatial index!");
        }
        
        if(event.getSource().equals(this.menuitem_database_insertdata)) {
            db.generateData();
            db.saveToMongo();
            LOGGER.log(Level.INFO, "Generated and saved data to Mongo!");
        }
        
        if(event.getSource().equals(this.menuitem_operations_loadallcarsandpetrolstations)) {
            
            this.obsCars.setAll(db.getAllCars());
            this.obsPetrolStations.setAll(db.getAllPetrolStations());
            LOGGER.log(Level.INFO, "Loaded all cars and petrol stations!");
        }
        
        if(event.getSource().equals(this.menuitem_operations_petrolstationsinneighborhood)) {
            Car c = this.listview_car_cars.getSelectionModel().getSelectedItem();
            double d = 0d;
            
            try {
                d = Double.parseDouble(this.textfield_distance.getText()) /* 1000*/;
            } catch(Exception e) {
                LOGGER.log(
                    Level.SEVERE,
                    e.getMessage()
                );
                return;
            }
            
            if(c == null) {
                LOGGER.log(
                    Level.WARNING,
                    "No car selected!"
                );
                return;
            }
            
            this.obsPetrolStations.setAll(db.getAllPetrolStationsNearbyCar(c, d));
            
            LOGGER.log(
                Level.INFO,
                "Filtered all petrol stations in a radius of {0}km which offer {1}!",
                new Object[] {d, c.getFuelType()}
            );
        }
        
        if(event.getSource().equals(this.menuitem_help_about)) {
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Help -> About");
            alert.setHeaderText(null);
            alert.setContentText(
                "(c) G. Ortner, J. Strasser\n"
                    + "\n"
                    + "All Lat/Long coordinates from:\n"
                    + "https://www.luftlinie.org/"
            );

            alert.showAndWait();
        }
    }

    private ObservableList<Car> obsCars = FXCollections.observableArrayList();
    private ObservableList<PetrolStation> obsPetrolStations = FXCollections.observableArrayList();
    
    @FXML
    void initialize() {        
        this.listview_car_cars.getSelectionModel().getSelectedItem();
        
        try {
            db = Database.getInstance("127.0.0.1");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        
        this.attachLoggerToLabel();
        
        this.listview_car_cars.setItems(obsCars);
        this.listview_petrolstation_petrolstations.setItems(obsPetrolStations);
    }
    
    private void attachLoggerToLabel() {
        LOGGER.addHandler(
            new Handler(){
                @Override
                public void publish(LogRecord record) {
                    Platform.runLater(() -> {
                        label_logger.setText(
                            String.format(
                                "[%1$tc]: [%4$s]: %5$s%n",
                                record.getMillis(),
                                record.getSourceClassName(),
                                record.getLoggerName(),
                                record.getLevel(),
                                MessageFormat.format(record.getMessage(), record.getParameters()),
                                record.getThrown()
                            )
                        );
                    });
                }

                @Override
                public void flush() {
                    Platform.runLater(() -> {
                        label_logger.setText("Flushed");
                    });
                }

                @Override
                public void close() throws SecurityException {

                }
            }
        );
    }
    
}
