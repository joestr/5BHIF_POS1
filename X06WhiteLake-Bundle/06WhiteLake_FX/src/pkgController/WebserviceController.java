/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgController;

import pkgData.MongoLogPositionEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Point;
import java.time.LocalDateTime;
import javafx.application.Platform;
import pkgMisc.Converter;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.Client;
import java.lang.reflect.Type;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author admin
 */
public class WebserviceController extends Thread {

    private final String shipname;
    private final FXMLDocumentController controller;
    private Point currentFXPosition;
    private boolean isActive = true;

    private final static String POSITION_RESOURCE = "position";
    private final static String LATEST_POSITION_RESOURCE = "latestPosition";
    private final String uri = "http://localhost:8080/MongoSchupfer/webresources";
    private ClientConfig config = null;
    private Client client = null;
    private WebResource service = null;

    public WebserviceController(String shipname, FXMLDocumentController controller) {
        this.shipname = shipname;
        this.controller = controller;
        config = new DefaultClientConfig();
        client = Client.create(config);
        service = client.resource(UriBuilder.fromUri(uri).build());
    }

    public synchronized void kill() {
        this.isActive = false;
    }

    @Override
    public void run() {
        try {
            while (isActive) {
                Platform.runLater(() -> controller.getFXCoordinatesOfCurrentTransition(this));
                synchronized (this) {
                    this.wait();
                }
                currentFXPosition = FXMLDocumentController.getRequestedFXPosition();
                String jsonString = new Gson().toJson(new MongoLogPositionEntry(shipname,
                        Converter.toSpatialPosition(currentFXPosition), LocalDateTime.now()));

                ClientResponse retValue;
                if (isActive) {
                    retValue = service.path(POSITION_RESOURCE).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonString);

                    System.out.println(currentFXPosition);
                    System.out.println(Converter.toSpatialPosition(currentFXPosition));
                    System.out.println(retValue.toString());
                    retValue = service.path(LATEST_POSITION_RESOURCE).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
                    Gson gson = new Gson();
                    String json = retValue.getEntity(String.class);
                    Type collectionType = new TypeToken<ArrayList<MongoLogPositionEntry>>() {
                    }.getType();
                    ArrayList<MongoLogPositionEntry> collPositions = gson.fromJson(json, collectionType);
                    Platform.runLater(() -> controller.refreshLatestPositions(collPositions));
                    Thread.sleep(1000);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
