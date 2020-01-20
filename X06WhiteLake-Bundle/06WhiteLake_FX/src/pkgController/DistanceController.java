/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgController;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.impl.MultiPartWriter;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import pkgData.SpatialPosition;

/**
 *
 * @author admin
 * Only for test purposes
 */
public class DistanceController {

    private final static String DISTANCE_RESOURCE = "distance/";
    private String uri = null;
    private ClientConfig config = null;
    private Client client = null;
    private WebResource service = null;

    
    //"http://localhost:8080/MongoSchupfer/webresources"
    public void setUri(String uri) throws Exception {
        this.uri = uri;
        config = new DefaultClientConfig();
        config.getClasses().add(MultiPartWriter.class); //concerning image

        client = Client.create(config);
        service = client.resource(getBaseURI());
    }

    private URI getBaseURI() {
        return UriBuilder.fromUri(uri).build();
    }

    public void getInfo(String shipName, SpatialPosition position) {
        ClientResponse response;
        response = service.path(DISTANCE_RESOURCE)
                .queryParam("shipname", shipName)
                .queryParam("long", String.valueOf(position.getLongitude()))
                .queryParam("lat", String.valueOf(position.getLatitude()))
                .get(ClientResponse.class);
        System.out.println(response.getEntity(String.class));
    }
}
