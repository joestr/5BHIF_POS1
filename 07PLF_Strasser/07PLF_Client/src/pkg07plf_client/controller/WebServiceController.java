/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07plf_client.controller;

import com.google.gson.Gson;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.Client;
import javax.ws.rs.core.MediaType;
import pkg07plf_client.data.DriverPosition;
import pkg07plf_client.data.VillagePosition;

/**
 *
 * @author admin
 */
public class WebServiceController extends Thread {

    private final static String VILLAGES_RESOURCE = "villages";
    private final static String DRIVERS_RESOURCE = "drivers";
    private final String uri = "http://127.0.0.1:8080/07PLF_WebServer/webresources";
    private ClientConfig config = null;
    private Client client = null;
    private WebResource service = null;

    public WebServiceController() {
        config = new DefaultClientConfig();
        client = Client.create(config);
        service = client.resource(UriBuilder.fromUri(uri).build());
    }
    
    public String sendGeoPosition(DriverPosition dp) {
        String jsonString = new Gson().toJson(dp, DriverPosition.class);
        ClientResponse retValue = service
            .path(DRIVERS_RESOURCE)
            .type(MediaType.APPLICATION_JSON)
            .post(ClientResponse.class, jsonString);
        
        return "POST created a " + retValue.getStatus() +
            " " + retValue.getResponseStatus().getReasonPhrase();
    }

    public String sendGeoPosition(VillagePosition vp) {
        String jsonString = new Gson().toJson(vp, VillagePosition.class);
        ClientResponse retValue = service
            .path(VILLAGES_RESOURCE)
            .type(MediaType.APPLICATION_JSON)
            .post(ClientResponse.class, jsonString);
        
        return "POST created a " + retValue.getStatus() +
            " " + retValue.getResponseStatus().getReasonPhrase();
    }

    public String getDriverNames(String city, int i) {
        ClientResponse retValue = service
            .path(DRIVERS_RESOURCE)
            .type(MediaType.APPLICATION_JSON)
            .get(ClientResponse.class);
        return "GET created a " + retValue.getStatus() +
            " " + retValue.getResponseStatus().getReasonPhrase();
    }
}
