package xyz.joestr.school._5bhif.pos1._01car;

import com.mongodb.MongoClient;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import xyz.joestr.school._5bhif.pos1._01car.classes.Car;
import xyz.joestr.school._5bhif.pos1._01car.classes.Database;
import xyz.joestr.school._5bhif.pos1._01car.classes.Owner;
import xyz.joestr.school._5bhif.pos1._01car.classes.Ownership;

public class FXMLController {

    @FXML
    private Label label;

    //<editor-fold defaultstate="collapsed" desc="Menuitems for database operations">
    @FXML
    private MenuItem menuitem_database_connect;
    
    @FXML
    private MenuItem menuitem_database_close;
    
    @FXML
    private MenuItem menuitem_database_createtextindex;
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Menuitems for car operations">
    @FXML
    private MenuItem menuitem_car_insert;
    
    @FXML
    private MenuItem menuitem_car_delete;
    
    @FXML
    private MenuItem menuitem_car_update;
    
    @FXML
    private MenuItem menuitem_car_replace;
    
    @FXML
    private MenuItem menuitem_car_findall;
    
    @FXML
    private MenuItem menuitem_car_findrelevance;
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="Menuitems for owner operations">
    @FXML
    private MenuItem menuitem_owner_insert;
    
    @FXML
    private MenuItem menuitem_owner_update;
    
    @FXML
    private MenuItem menuitem_owner_delete;
    
    @FXML
    private MenuItem menuitem_owner_replace;
    
    @FXML
    private MenuItem menuitem_owner_findall;
    
    @FXML
    private MenuItem menuitem_owner_findrelevance;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Menuitems for ownership operations">
    @FXML
    private MenuItem menuitem_ownership_addtocar;
    
    @FXML
    private MenuItem menuitem_ownership_removefromcar;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Car related fields">
    @FXML
    private TextField textfield_database_ip;
    
    @FXML
    private TextField textfield_car_name;
    
    @FXML
    private TextField textfield_car_productionyear;
    
    @FXML
    private TextField textfield_car_horsepower;
    
    @FXML
    private ListView<Car> listview_cars;
    
    @FXML
    private TextArea textarea_car_description;
    
    @FXML
    private TextField textfield_car_search;
    
    @FXML
    private Button button_car_search_submit;
    //</editor-fold>

    @FXML
    private Text text_label;

    //<editor-fold defaultstate="collapsed" desc="Owner related fields">
    @FXML
    private TextField textfield_owner_name;
    
    @FXML
    private TextArea textfield_owner_details;
    
    @FXML
    private DatePicker textfield_owner_birth;
    
    @FXML
    private ListView<Owner> listview_owners;
    
    @FXML
    private TextField owner_search_field;
    
    @FXML
    private Button button_owner_search_submit;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Ownership related fields">
    @FXML
    private ListView<Ownership> listview_ownerships;
    
    @FXML
    private DatePicker datepicker_ownership_start;
    
    @FXML
    private DatePicker datepicker_ownership_end;
    //</editor-fold>

    @FXML
    void onAction(ActionEvent event) {
        if(event.getSource().equals(button_car_search_submit)) {
            try {
                this.cars.setAll(this.db.getAllCarsOrderedByRelevance(this.textfield_car_search.getText()));
                LOGGER.log(
                    Level.INFO,
                    "Loaded all cars by relevance: '{0}'",
                    this.textfield_car_search.getText()
                );
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(button_owner_search_submit)) {
            try {
                this.owners.setAll(this.db.getAllOwnersOrderedByRelevance(this.textfield_car_search.getText()));
                LOGGER.log(
                    Level.INFO,
                    "Loaded all owners by relevance: '{0}'",
                    this.textfield_car_search.getText()
                );
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }

    @FXML
    void onActionMenuCar(ActionEvent event) {
        if(event.getSource().equals(this.menuitem_car_insert)) {
            Car c = new Car(
                textfield_car_name.getText(),
                Integer.parseInt(textfield_car_horsepower.getText()),
                Integer.parseInt(textfield_car_productionyear.getText()),
                textarea_car_description.getText()
            );
            try {
                db.insertCar(c);
                LOGGER.log(Level.INFO, "Inserted car: '{0}'", c.toString());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_car_update)) {
            Car c = new Car(
                textfield_car_name.getText(),
                Integer.parseInt(textfield_car_horsepower.getText()),
                Integer.parseInt(textfield_car_productionyear.getText()),
                textarea_car_description.getText()
            );
            c.setId(this.selectedCar.getId());
            try {
                db.updateCar(c);
                LOGGER.log(Level.INFO, "Updated car: '{0}'", c);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_car_replace)) {
            Car c = new Car(
                textfield_car_name.getText(),
                Integer.parseInt(textfield_car_horsepower.getText()),
                Integer.parseInt(textfield_car_productionyear.getText()),
                textarea_car_description.getText()
            );
            try {
                db.replaceCar(c);
                LOGGER.log(Level.INFO, "Replaced car: '{0}'", c.toString());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_car_delete)) {
            try {
                db.deleteCar(this.selectedCar);
                LOGGER.log(Level.INFO, "Deleted car: '{0}'", this.selectedCar.toString());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_car_findall)) {
            try {
                this.cars.setAll(db.getAllCars());
                LOGGER.log(Level.INFO, "Loaded all cars");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_car_findrelevance)) {
            try {
                this.cars.setAll(db.getAllCarsOrderedByRelevance(this.textfield_car_search.getText()));
                LOGGER.log(Level.INFO, "Loaded all cars by relevance: '{0}'", this.textfield_car_search.getText());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }

    @FXML
    void onActionMenuDatabase(ActionEvent event) {
        if(event.getSource().equals(this.menuitem_database_connect)) {
            try {
                db = Database.getInstance(textfield_database_ip.getText());
                LOGGER.log(Level.INFO, "Successfully connected to database");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
            
            
        }
        
        if(event.getSource().equals(this.menuitem_database_createtextindex)) {
            try {
                db.createTextIndex();
                LOGGER.log(Level.INFO, "Created inidizes");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_database_close));
    }

    @FXML
    void onActionMenuOwner(ActionEvent event) {
        if(event.getSource().equals(this.menuitem_owner_insert)) {
            Owner o = new Owner(
                this.textfield_owner_name.getText(),
                this.textfield_owner_details.getText(),
                this.textfield_owner_birth.getValue()
            );
            try {
                db.insertOwner(o);
                LOGGER.log(Level.INFO, "Inserted owner: '{0}'", o.toString());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_owner_update)) {
            Owner o = new Owner(
                this.textfield_owner_name.getText(),
                this.textfield_owner_details.getText(),
                this.textfield_owner_birth.getValue()
            );
            o.setId(this.selectedOwner.getId());
            try {
                db.insertOwner(o);
                LOGGER.log(Level.INFO, "Updated owner: '{0}'", o.toString());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_owner_replace)) {
            Owner o = new Owner(
                this.textfield_owner_name.getText(),
                this.textfield_owner_details.getText(),
                this.textfield_owner_birth.getValue()
            );
            try {
                db.replaceOwner(o);
                LOGGER.log(Level.INFO, "Replaced owner: '{0}'", o.toString());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_owner_delete)) {
            try {
                db.insertOwner(this.selectedOwner);
                LOGGER.log(Level.INFO, "Deleted owner: '{0}'", this.selectedOwner.toString());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_owner_findall)) {
            try {
                this.owners.setAll(db.getAllOwners());
                LOGGER.log(Level.INFO, "Loaded all owners");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_owner_findrelevance)) {
            try {
                this.owners.setAll(db.getAllOwnersOrderedByRelevance(this.owner_search_field.getText()));
                LOGGER.log(Level.INFO, "Loaded all owners by relevance: '{0}'", this.owner_search_field.getText());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }

    @FXML
    void onActionMenuOwnership(ActionEvent event) {
        if(event.getSource().equals(this.menuitem_ownership_addtocar)) {
            Ownership o = new Ownership(
                this.datepicker_ownership_start.getValue(),
                this.datepicker_ownership_end.getValue(),
                this.selectedOwner.getId()
            );
            
            try {
                db.addOwnershipToCar(this.selectedCar, o);
                LOGGER.log(Level.INFO, "Added ownership '{0}' to car '{1}'", new Object[] { o.toString(), this.selectedCar.toString() });
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        
        if(event.getSource().equals(this.menuitem_ownership_removefromcar)) {
            try {
                db.removeOwnershipFromCar(this.selectedCar, this.selectedOwnership);
                LOGGER.log(Level.INFO, "Removed ownership '{0}' from car '{1}'", new Object[] { this.selectedOwnership.toString(), this.selectedCar.toString() });
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }

    MongoClient mc;
    Database db;
    
    private ObservableList<Car> cars;
    private ObservableList<Ownership> ownerships;
    private ObservableList<Owner> owners;
    
    Car selectedCar;
    Owner selectedOwner;
    Ownership selectedOwnership;
    
    public static Logger LOGGER;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        cars = FXCollections.observableArrayList();
        owners = FXCollections.observableArrayList();
        ownerships = FXCollections.observableArrayList();
        
        this.listview_cars.setItems(cars);
        this.listview_owners.setItems(owners);
        this.listview_ownerships.setItems(ownerships);
        
        this.listview_cars.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Car> observable, Car oldValue, Car newValue) -> {
            if (newValue != null) {
                selectedCar = newValue;
                textfield_car_name.setText(selectedCar.getName());
                textfield_car_productionyear.setText("" + selectedCar.getYear());
                textfield_car_horsepower.setText("" + selectedCar.getHp());
                textarea_car_description.setText(selectedCar.getDescription());
                
                Collection<Ownership> carOwnerships = db.getOwnershipsOfCar(selectedCar);
                if(carOwnerships == null) {
                    ownerships.clear();
                } else {
                    ownerships.setAll(carOwnerships);
                }
            }
        });
        
        this.listview_owners.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Owner> observable, Owner oldValue, Owner newValue) -> {
            if (newValue != null) {
                selectedOwner = newValue;
                textfield_owner_details.setText(selectedOwner.getDetails());
                textfield_owner_name.setText(selectedOwner.getName());
                textfield_owner_birth.setValue(selectedOwner.getBirth());
            }
            
            cars.setAll(db.getCarsOfOwner(selectedOwner));
        });
        
        this.listview_ownerships.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Ownership> observable, Ownership oldValue, Ownership newValue) -> {
            if (newValue != null) {
                selectedOwnership = newValue;
                Owner o = db.getOwnerById(selectedOwnership.getOwnerId());
                textfield_owner_details.setText(o.getDetails());
                textfield_owner_name.setText(o.getName());
                textfield_owner_birth.setValue(o.getBirth());
            }
        });
                   Formatter formatter = new SimpleFormatter();
                   
        LOGGER = Logger.getLogger("CarV3");
        
        ConsoleHandler ch;
        
        LOGGER.addHandler(
            new Handler(){
                @Override
                public void publish(LogRecord record) {
                    Platform.runLater(() -> {
                        text_label.setText(
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
                        text_label.setText("Flushed");
                    });
                }

                @Override
                public void close() throws SecurityException {

                }
            }
        );
        
        // Converter
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                      DateTimeFormatter.ofPattern("dd.MM.yyyy");
            
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        this.datepicker_ownership_start.setConverter(converter);
        this.datepicker_ownership_end.setConverter(converter);
        this.textfield_owner_birth.setConverter(converter);
    }
}
