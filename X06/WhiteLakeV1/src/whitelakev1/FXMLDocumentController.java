/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whitelakev1;

import com.mongodb.client.model.geojson.Point;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pkgData.Position;
import pkgData.Worker;
import pkgMisc.Controller;
import static pkgMisc.SpatialConstants.LENGTH_OF_LAKE;
import static pkgMisc.SpatialConstants.TECHENDORF;
import static whitelakev1.AnimationConstants.DOLOMITENBLICK_X;
import static whitelakev1.AnimationConstants.TECHENDORF_X;

/**
 *
 * @author admin
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Text txtMessages;

    @FXML
    private Button btnRonacherfels;

    @FXML
    private Button btnPaternzipf;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    @FXML
    private ImageView imageView;

    @FXML
    private ImageView boat;

    @FXML
    private Button btnStartCooLog;

    @FXML
    private Button btnStopCooLog;
    
    @FXML
    private TextField txtShipname;
    
    @FXML
    private Label txtMsg;
    
    @FXML
    private ListView<Position> lvLogs;

    private PathTransition currentPathTransition;
    private double destinationX;
    private double destinationY;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            if (event.getSource().equals(btnStart)) {
                System.out.println("You clicked me!");
                MoveToDolomitenblick();
                txtMsg.setText("Ship started moving");
            } else if (event.getSource().equals(btnPaternzipf)) {
                currentPathTransition.stop();
                MoveToPaternzipf();
                txtMsg.setText("Ship moves to Paternzipf");
            } else if (event.getSource().equals(btnStop)) {
                currentPathTransition.stop();
                txtMsg.setText("Ship stopped moving");
            } else if (event.getSource().equals(btnRonacherfels)) {
                currentPathTransition.stop();
                MoveToRonacherfels();
                txtMsg.setText("Ship moves to Ronacherfels");
            } else if (event.getSource().equals(btnStartCooLog)) {
                worker.setShipname(txtShipname.getText());
                thread = new Thread((Runnable) worker);
                thread.start();
                txtMsg.setText("logging started");
            } else if (event.getSource().equals(btnStopCooLog)) {
                worker.setEnd(false);
                thread.stop();
                txtMsg.setText("logging stopped");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
 
    public void addLog(ArrayList<Position> positions) throws Exception {
        obsListPositions.addAll(positions);
    }
    
    public Point doubleToPoint() {
        double conversionFactor = LENGTH_OF_LAKE/( DOLOMITENBLICK_X - TECHENDORF_X);
        return new Point(new com.mongodb.client.model.geojson.Position( TECHENDORF.getLongitude() + (currentPathTransition.getNode().getTranslateX() * conversionFactor), TECHENDORF.getLatitude() + ( currentPathTransition.getNode().getTranslateY() * conversionFactor)));
    }

    private void MoveToDolomitenblick() {
        System.out.println("Moving to Dolomitenblick");
        Path path = new Path();
        destinationX = AnimationConstants.DOLOMITENBLICK_X + 20;
        destinationY = AnimationConstants.DOLOMITENBLICK_Y + 10;

        path.getElements().add(new MoveTo(currentPathTransition.getNode().getTranslateX() + 20+1, currentPathTransition.getNode().getTranslateY() + 10));

        path.getElements().add(new LineTo(destinationX, destinationY));

        currentPathTransition = new PathTransition();

        currentPathTransition.setDuration(Duration.millis(3000));

        currentPathTransition.setPath(path);
        currentPathTransition.setNode(this.boat);
        currentPathTransition.setOnFinished(eventArg -> MoveToTechendorf());
        currentPathTransition.play();

    }

    private void MoveToTechendorf() {
        System.out.println("Moving to Techendorf");
        Path path1 = new Path();
        destinationX = AnimationConstants.TECHENDORF_X + 20;
        destinationY = AnimationConstants.TECHENDORF_Y + 10;

        path1.getElements().add(new MoveTo(currentPathTransition.getNode().getTranslateX() + 20, currentPathTransition.getNode().getTranslateY() + 10));
        path1.getElements().add(new LineTo(destinationX, destinationY));

        currentPathTransition = new PathTransition();

        currentPathTransition.setDuration(Duration.millis(3000));

        currentPathTransition.setPath(path1);
        currentPathTransition.setNode(this.boat);
        currentPathTransition.setOnFinished(eventArgs -> MoveToDolomitenblick());
        currentPathTransition.play();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentPathTransition = new PathTransition(
                Duration.ZERO,
                new Path(
                        new MoveTo(AnimationConstants.TECHENDORF_X + 20, AnimationConstants.TECHENDORF_Y + 10),
                        new LineTo(AnimationConstants.TECHENDORF_X + 20, AnimationConstants.TECHENDORF_Y + 10)),
                boat);
        try {
            controller.setUri("http://localhost:9190/WhiteLakeWebServer/");
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        obsListPositions = FXCollections.observableArrayList();
        lvLogs.setItems(obsListPositions);
        worker = new Worker(this);
    }

    private void MoveToRonacherfels() {
        System.out.println(currentPathTransition.getNode().getTranslateX() + ", " + currentPathTransition.getNode().getTranslateY());
        Path path1 = new Path();

        path1.getElements().add(new MoveTo(currentPathTransition.getNode().getTranslateX() + 20, currentPathTransition.getNode().getTranslateY() + 10));
        path1.getElements().add(new LineTo(250 + 20, -10 + 10));

        PathTransition temp = new PathTransition();

        temp.setDuration(Duration.millis(3000));

        temp.setPath(path1);
        temp.setNode(this.boat);

        //carry on to specified destiny
        temp.setOnFinished(value -> {
            if (destinationX == AnimationConstants.DOLOMITENBLICK_X + 20) {
                MoveToDolomitenblick();
            } else {
                MoveToTechendorf();
            }
        });

        temp.play();

    }

    private void MoveToPaternzipf() {
        System.out.println(currentPathTransition.getNode().getTranslateX() + ", " + currentPathTransition.getNode().getTranslateY());
        Path path1 = new Path();

        path1.getElements().add(new MoveTo(currentPathTransition.getNode().getTranslateX() + 20, currentPathTransition.getNode().getTranslateY() + 10));
        path1.getElements().add(new LineTo(100 + 20, 40 + 10));

        PathTransition temp = new PathTransition();

        temp.setDuration(Duration.millis(3000));

        temp.setPath(path1);
        temp.setNode(this.boat);

        //carry on to specified destiny
        temp.setOnFinished(value -> {
            if (destinationX == AnimationConstants.DOLOMITENBLICK_X + 20) {
                MoveToDolomitenblick();
            } else {
                MoveToTechendorf();
            }
        });

        temp.play();

    }
    private final Controller controller = new Controller();
    private Thread thread;
    private ObservableList<Position> obsListPositions;
    private Worker worker;
}
