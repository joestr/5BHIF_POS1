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
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
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

    @FXML
    void onActionButton(ActionEvent event) {
        
        if(event.equals(this.button_start)) {
            
        }
        
        this.polyline_stations.getPoints().get(0);
        this.polyline_stations.getPoints().get(1);
        
        Path path = new Path();
        
        path.getElements().add(
            new MoveTo(
            this.stations.get(0).getX(),
            this.stations.get(0).getY()
        ));
        path.getElements().add(
            new LineTo(
                this.stations.get(2).getX(),
                this.stations.get(2).getY()
        ));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(3000));
        pathTransition.setPath(path);
        pathTransition.setNode(this.imageview_titanic);
        pathTransition.play();
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
    }    
    
}
