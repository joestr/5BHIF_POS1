/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg04anim;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import static javafx.animation.Animation.INDEFINITE;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

/**
 *
 * @author Joel
 */
public class FXMLDocumentController implements Initializable, Constants {
    
    @FXML
    private ImageView imageview_map;
    
    @FXML
    private Button button_start;

    @FXML
    private Button button_stop;

    @FXML
    private Label label_logger;

    @FXML
    private Button button_ronacherfels;

    @FXML
    private Button button_paternzipf;
    
    @FXML
    private ImageView imageview_titanic;
    
    @FXML
    private Polyline polyline_stations;
    
    // custom;
    ArrayList<Point2D> stations = new ArrayList<>();
    PathTransition pathTransitionFromDolomitenblickToTechendorf = new PathTransition();
    PathTransition pathTransitionFromTechendorfToDolomitenblick = new PathTransition();
    boolean isFromDolomitenblickToTechendorf = true;
    
    Path pathFromDolomitenblickToTechendorf = new Path();
    Path pathFromTechendorfToDolomitenblick = new Path();
    
    PathTransition currentPathTranisition;
    
    PathTransition custom = new PathTransition();
    Path customPath = new Path();

    @FXML
    void onActionButton(ActionEvent event) {
        
        /**
         * stations:
         * 0 -> Dolomitenblick
         * 1 -> Paternzipf
         * 2 -> Techendorf
         * 3 -> Ronacherfels
         */
        
        if(event.getSource().equals(this.button_start)) {
            
            this.currentPathTranisition =
                this.pathTransitionFromDolomitenblickToTechendorf;
            this.pathTransitionFromDolomitenblickToTechendorf.play();
        }
        
        if(event.getSource().equals(this.button_stop)) {
            
            this.pathTransitionFromDolomitenblickToTechendorf.stop();
            this.pathTransitionFromTechendorfToDolomitenblick.stop();
        }
        
        if(event.getSource().equals(this.button_paternzipf)) {
            
            this.pathTransitionFromDolomitenblickToTechendorf.stop();
            this.pathTransitionFromTechendorfToDolomitenblick.stop();
            
            Path tempPath = new Path();
            
            tempPath.getElements().add(new MoveTo(
                this.currentPathTranisition.getNode().getTranslateX(),
                this.currentPathTranisition.getNode().getTranslateY()
            ));
            
            tempPath.getElements().add(new LineTo(
                this.stations.get(1).getX(),
                this.stations.get(1).getY()
            ));
            
            if(this.isFromDolomitenblickToTechendorf) {
                tempPath.getElements().add(new LineTo(
                    this.stations.get(2).getX(),
                    this.stations.get(2).getY()
                ));
            } else {
                tempPath.getElements().add(new LineTo(
                    this.stations.get(0).getX(),
                    this.stations.get(0).getY()
                ));
            }
            
            PathTransition tempPathTransition = new PathTransition();
            
            tempPathTransition.setDuration(Duration.millis(3000));
            tempPathTransition.setPath(tempPath);
            tempPathTransition.setNode(this.imageview_titanic);
            tempPathTransition.setOnFinished((ev) -> {
                if(this.isFromDolomitenblickToTechendorf) {
                    isFromDolomitenblickToTechendorf = false;
                    currentPathTranisition = pathTransitionFromTechendorfToDolomitenblick;
                    pathTransitionFromTechendorfToDolomitenblick.play();
                } else {
                    isFromDolomitenblickToTechendorf = true;
                    currentPathTranisition = pathTransitionFromDolomitenblickToTechendorf;
                    pathTransitionFromDolomitenblickToTechendorf.play();
                }
            });
            
            this.currentPathTranisition = tempPathTransition;
            
            tempPathTransition.play();
        }
        
        if(event.getSource().equals(this.button_ronacherfels)) {
            
            this.pathTransitionFromDolomitenblickToTechendorf.stop();
            this.pathTransitionFromTechendorfToDolomitenblick.stop();
            
            Path tempPath = new Path();
            
            tempPath.getElements().add(new MoveTo(
                this.currentPathTranisition.getNode().getTranslateX(),
                this.currentPathTranisition.getNode().getTranslateY()
            ));
            
            tempPath.getElements().add(new LineTo(
                this.stations.get(3).getX(),
                this.stations.get(3).getY()
            ));
            
            if(this.isFromDolomitenblickToTechendorf) {
                tempPath.getElements().add(new LineTo(
                    this.stations.get(2).getX(),
                    this.stations.get(2).getY()
                ));
            } else {
                tempPath.getElements().add(new LineTo(
                    this.stations.get(0).getX(),
                    this.stations.get(0).getY()
                ));
            }
            
            PathTransition tempPathTransition = new PathTransition();
            
            tempPathTransition.setDuration(Duration.millis(3000));
            tempPathTransition.setPath(tempPath);
            tempPathTransition.setNode(this.imageview_titanic);
            tempPathTransition.setOnFinished((ev) -> {
                if(this.isFromDolomitenblickToTechendorf) {
                    isFromDolomitenblickToTechendorf = false;
                    currentPathTranisition = pathTransitionFromTechendorfToDolomitenblick;
                    pathTransitionFromTechendorfToDolomitenblick.play();
                } else {
                    isFromDolomitenblickToTechendorf = true;
                    currentPathTranisition = pathTransitionFromDolomitenblickToTechendorf;
                    pathTransitionFromDolomitenblickToTechendorf.play();
                }
            });
            
            this.currentPathTranisition = tempPathTransition;
            
            tempPathTransition.play();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        for(int i = 0; i < this.polyline_stations.getPoints().size(); i++) {
            
            this.stations.add(
                new Point2D(
                    this.polyline_stations.getPoints().get(i),
                    this.polyline_stations.getPoints().get(++i)
                )
            );
        }
        
        pathFromDolomitenblickToTechendorf.getElements().add(
            new MoveTo(
                this.stations.get(0).getX(),
                this.stations.get(0).getY()
        ));
        pathFromDolomitenblickToTechendorf.getElements().add(
            new LineTo(
                this.stations.get(2).getX(),
                this.stations.get(2).getY()
        ));
        
        pathFromTechendorfToDolomitenblick.getElements().add(
            new MoveTo(
                this.stations.get(2).getX(),
                this.stations.get(2).getY()
        ));
        pathFromTechendorfToDolomitenblick.getElements().add(
            new LineTo(
                this.stations.get(0).getX(),
                this.stations.get(0).getY()
        ));
        
        pathTransitionFromDolomitenblickToTechendorf.setDuration(Duration.millis(3000));
        pathTransitionFromDolomitenblickToTechendorf.setPath(pathFromDolomitenblickToTechendorf);
        pathTransitionFromDolomitenblickToTechendorf.setNode(this.imageview_titanic);
        pathTransitionFromDolomitenblickToTechendorf.setOnFinished((ev) -> {
            
            isFromDolomitenblickToTechendorf = false;
            currentPathTranisition = pathTransitionFromDolomitenblickToTechendorf;
            pathTransitionFromTechendorfToDolomitenblick.play();
            
            
        });
        
        pathTransitionFromTechendorfToDolomitenblick.setDuration(Duration.millis(3000));
        pathTransitionFromTechendorfToDolomitenblick.setPath(pathFromTechendorfToDolomitenblick);
        pathTransitionFromTechendorfToDolomitenblick.setNode(this.imageview_titanic);
        pathTransitionFromTechendorfToDolomitenblick.setOnFinished((ev) -> {
            
            pathTransitionFromDolomitenblickToTechendorf.play();
            currentPathTranisition = pathTransitionFromTechendorfToDolomitenblick;
            isFromDolomitenblickToTechendorf = true;
        });
    }    
    
}
