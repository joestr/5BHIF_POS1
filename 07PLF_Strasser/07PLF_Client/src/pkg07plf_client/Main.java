/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07plf_client;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import java.time.LocalDateTime;
import pkg07plf_client.controller.WebServiceController;
import pkg07plf_client.data.DriverPosition;
import pkg07plf_client.data.VillagePosition;

/**
 *
 * @author Joel
 */
public class Main {

    static private WebServiceController ws;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            initThings();
            sendGeoPositionsOfVillages();
            sendGeoPositionsOfDrivers();
            findNextDrivers();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void sendGeoPositionsOfDrivers() throws Exception {
        System.out.println("send driver positions ...");
        DriverPosition dp = new DriverPosition(null, "Ferndorfer Felix",
                new Point(new Position(13.295269, 46.716978)),
                LocalDateTime.of(2019, 12, 24, 11, 30));
        System.out.println("..." + ws.sendGeoPosition(dp));
        dp = new DriverPosition(null, "Feistritzer Frieda",
                new Point(new Position(13.411547, 46.698773)),
                LocalDateTime.of(2019, 12, 24, 11, 25));
        System.out.println("..." + ws.sendGeoPosition(dp));
        dp = new DriverPosition(null, "Villacher Vroni",
                new Point(new Position(13.007603, 45.98653)),
                LocalDateTime.of(2019, 12, 24, 11, 35));
        System.out.println("..." + ws.sendGeoPosition(dp));
        System.out.println("send driver positions done");
    }
    
    private static void sendGeoPositionsOfVillages() throws Exception {
        System.out.println("send village positions ...");
        VillagePosition vp = new VillagePosition(null, "Ferndorf",
                new Point(new Position(13.295269, 46.716978)));
        System.out.println("..." + ws.sendGeoPosition(vp));
        vp = new VillagePosition(null, "Fresach",
                new Point(new Position(13.375980, 46.704123)));
        System.out.println("..." + ws.sendGeoPosition(vp));
        vp = new VillagePosition(null, "Paternion",
                new Point(new Position(13.347603, 46.703653)));
        System.out.println("..." + ws.sendGeoPosition(vp));
        vp = new VillagePosition(null, "Feistritz",
                new Point(new Position(13.411547, 46.698773)));
        System.out.println("..." + ws.sendGeoPosition(vp));
        System.out.println("send village positions done");
    }

    private static void initThings() throws Exception {
        ws = new WebServiceController();
    }

    private static void findNextDrivers() throws Exception {
        System.out.println("find next drivers");
        
        System.out.println("...(Feistritz within " + 100 + "): " + ws.getDriverNames("Feistritz", 100));
        System.out.println("...(Paternion within " + 100 + "): " + ws.getDriverNames("Paternion", 100));
        System.out.println("...(Paternion within " + 80 + "): " + ws.getDriverNames("Paternion", 80));
        System.out.println("...(Klagenfurt within " + 80 + "): " + ws.getDriverNames("Klagenfurt", 80));
        System.out.println("find next drivers done");
    }
    
}
