/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import pkgMisc.Controller;
import whitelakev1.FXMLDocumentController;

/**
 *
 * @author schueler
 */
public class Worker extends Thread {

    private FXMLDocumentController fxController;
    private boolean end;
    private String shipname;
    private final Controller controller = new Controller();

    public Worker(FXMLDocumentController controller) {
        this.fxController = controller;
        try {
            this.controller.setUri("http://localhost:9190/WhiteLakeWebServer/");
        } catch (Exception ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
        end = true;
    }

    public void run() {
        try {
            while (end) {
                Thread.sleep(1000);
                Position position = new Position(shipname, fxController.doubleToPoint(), LocalDateTime.now());
                String res = controller.addPosition(position);
                Platform.runLater(() -> {
                    try {
                        fxController.addLog(controller.getPositions());
                    } catch (Exception ex) {
                        Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                System.out.println("RES POS: " + res);
                System.out.println("saved Position: " + position);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
    }
    
    public void setEnd(boolean end) {
        this.end = end;
    }
}
