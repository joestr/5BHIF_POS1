/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgController;

import pkgMisc.AnimationConstants;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pkgData.MongoLogPositionEntry;
import pkgData.SpatialPosition;
import pkgMisc.Converter;
import pkgMisc.SpatialConstants;
import pkgMisc.WebserviceControllable;

/**
 *
 * @author admin
 */
public class FXMLDocumentController implements Initializable, WebserviceControllable {

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
    private TextField txtShipname;

    @FXML
    private Button btnStartCooLog;

    @FXML
    private Button btnStopCooLog;

    @FXML
    private Button btnSchupf;

    @FXML
    private ListView<MongoLogPositionEntry> lvLatestPosition;

    private double destinationX;
    private double destinationY;
    private WebserviceController webserviceController;
    private PathTransition currentPathTransition;
    private Destination actualDesitination;
    private ObservableList<MongoLogPositionEntry> obstPositions;

    private static Point requestedFXPosition;

    private enum Destination {
        Techendorf, DolomitenBlick
    };

    @Override
    public void refreshLatestPositions(ArrayList<MongoLogPositionEntry> collPositions) {
        obstPositions.setAll(collPositions);
    }

    @Override
    public void getFXCoordinatesOfCurrentTransition(WebserviceController sender) {
        synchronized (sender) {
            requestedFXPosition = new Point((int) currentPathTransition.getNode().getTranslateX(), (int) currentPathTransition.getNode().getTranslateY());
            sender.notify();
        }
    }

    public static Point getRequestedFXPosition() {
        return requestedFXPosition;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource().equals(btnStart)) {
            if ("".equals(txtShipname.getText())) {
                txtMessages.setText("ship name must be specified");
            } else {
                btnStartCooLog.setDisable(false);
                btnStop.setDisable(false);
                MoveToDolomitenblick();
                txtMessages.setText("ship is moving");
            }
        } else if (event.getSource().equals(btnPaternzipf)) {
            currentPathTransition.stop();
            MoveToPaternzipf();
        } else if (event.getSource().equals(btnRonacherfels)) {
            currentPathTransition.stop();
            MoveToRonacherfels();
        } else if (event.getSource().equals(btnStop)) {
            currentPathTransition.stop();
            if (webserviceController != null) {
                webserviceController.kill();
                txtMessages.setText("stopped ship (and logging)");
            }
        } else if (event.getSource().equals(btnStartCooLog)) {
            btnStopCooLog.setDisable(false);
            webserviceController = new WebserviceController(txtShipname.getText(), this);
            webserviceController.start();
            txtMessages.setText("logging started");
        } else if (event.getSource().equals(btnStopCooLog)) {
            webserviceController.kill();
            txtMessages.setText("logging stopped");
        }
    }

    @FXML
    void handleClick(MouseEvent event) {
        System.out.println(event.getX() + ", " + event.getY());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obstPositions = FXCollections.observableArrayList();
        lvLatestPosition.setItems(obstPositions);
        currentPathTransition = new PathTransition(
                Duration.ZERO,
                new Path(
                        new MoveTo(AnimationConstants.TECHENDORF_X, AnimationConstants.TECHENDORF_Y),
                        new LineTo(AnimationConstants.TECHENDORF_X, AnimationConstants.TECHENDORF_Y)),
                boat);
    }

//<editor-fold defaultstate="collapsed" desc="moving">
    private void MoveToTechendorf() {
        System.out.println("Moving to Techendorf");
        Path path1 = new Path();
        destinationX = AnimationConstants.TECHENDORF_X;
        destinationY = AnimationConstants.TECHENDORF_Y;

        path1.getElements().add(new MoveTo(currentPathTransition.getNode().getTranslateX() + 20, currentPathTransition.getNode().getTranslateY() + 10));
        path1.getElements().add(new LineTo(destinationX, destinationY));

        currentPathTransition = new PathTransition();

        currentPathTransition.setDuration(Duration.millis(3000));

        currentPathTransition.setPath(path1);
        currentPathTransition.setNode(this.boat);
        currentPathTransition.setOnFinished(eventArgs -> MoveToDolomitenblick());
        actualDesitination = Destination.Techendorf;
        currentPathTransition.play();
    }

    private void MoveToDolomitenblick() {
        System.out.println("Moving to Dolomitenblick");
        Path path = new Path();
        destinationX = AnimationConstants.DOLOMITENBLICK_X;
        destinationY = AnimationConstants.DOLOMITENBLICK_Y;

        path.getElements().add(new MoveTo(currentPathTransition.getNode().getTranslateX() + 20, currentPathTransition.getNode().getTranslateY() + 10));

        path.getElements().add(new LineTo(destinationX, destinationY));

        currentPathTransition = new PathTransition();

        currentPathTransition.setDuration(Duration.millis(3000));

        currentPathTransition.setPath(path);
        currentPathTransition.setNode(this.boat);
        currentPathTransition.setOnFinished(eventArg -> MoveToTechendorf());
        actualDesitination = Destination.DolomitenBlick;
        currentPathTransition.play();
    }

    private void MoveToRonacherfels() {
        System.out.println(currentPathTransition.getNode().getTranslateX() + ", " + currentPathTransition.getNode().getTranslateY());
        Path path1 = new Path();

        path1.getElements().add(new MoveTo(currentPathTransition.getNode().getTranslateX() + 20, currentPathTransition.getNode().getTranslateY() + 10));
        path1.getElements().add(new LineTo(AnimationConstants.RONACHERFELS_X + 1, AnimationConstants.RONACHERFELS_Y + 1));

        currentPathTransition = new PathTransition();

        currentPathTransition.setDuration(Duration.millis(3000));

        currentPathTransition.setPath(path1);
        currentPathTransition.setNode(this.boat);

        //carry on to specified destiny
        currentPathTransition.setOnFinished(value -> {
            if (actualDesitination == Destination.DolomitenBlick) {
                MoveToDolomitenblick();
            } else {
                MoveToTechendorf();
            }
        });

        currentPathTransition.play();

    }

    private void MoveToPaternzipf() {
        System.out.println(currentPathTransition.getNode().getTranslateX() + ", " + currentPathTransition.getNode().getTranslateY());
        Path path1 = new Path();

        path1.getElements().add(new MoveTo(currentPathTransition.getNode().getTranslateX() + 20, currentPathTransition.getNode().getTranslateY() + 10));
        path1.getElements().add(new LineTo(AnimationConstants.PATERNZIPF_X, AnimationConstants.PATERNZIPF_Y));

        currentPathTransition = new PathTransition();

        currentPathTransition.setDuration(Duration.millis(3000));

        currentPathTransition.setPath(path1);
        currentPathTransition.setNode(this.boat);

        //carry on to specified destiny
        currentPathTransition.setOnFinished(value -> {
            if (actualDesitination == Destination.DolomitenBlick) {
                MoveToDolomitenblick();
            } else {
                MoveToTechendorf();
            }
        });

        currentPathTransition.play();
    }
//</editor-fold>
}
