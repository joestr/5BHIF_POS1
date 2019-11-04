package pkg01cargeo;

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class FXMLDocumentController {

    public static final Logger LOGGER = Logger.getLogger("01CarGeo");
    public Database db = null;
    
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
        if(event.getSource().equals(this.menuitem_database_initialize)) {
            try {
                db.generateData();
            } catch (Exception ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            db.saveToMongo();
        }
        
        if(event.getSource().equals(this.menuitem_database_createspatialindex)) {
            
        }
        
        if(event.getSource().equals(this.menuitem_database_readcarsandpetrolstations)) {
            
        }
        
        if(event.getSource().equals(this.menuitem_database_petrolstationsinneighborhood)) {
            
        }
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
        
        try {
            db = Database.getInstance("127.0.0.1");
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.attachLoggerToLabel();
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
