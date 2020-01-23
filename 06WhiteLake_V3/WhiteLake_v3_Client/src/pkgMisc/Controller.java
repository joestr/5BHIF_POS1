/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMisc;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.impl.MultiPartWriter;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import javax.ws.rs.Consumes;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import pkgData.Position;

/**
 *
 * @author schueler
 */
public class Controller {
    private String uri = null;
    private ClientConfig config = null;
    private Client client = null;
    private WebResource service = null;
    
    public void setUri(String uri) throws Exception {
        this.uri = uri;
        config = new DefaultClientConfig();
        config.getClasses().add(MultiPartWriter.class); 

        client = com.sun.jersey.api.client.Client.create(config);
        service = client.resource(getBaseURI());
    }
    
    private URI getBaseURI() {
        return UriBuilder.fromUri(uri).build();
    }
    
    public String addPosition(Position position) throws Exception {
        ClientResponse retValue;
        Gson gson = new Gson();
        String jsonPosition = gson.toJson(position, Position.class);
        retValue = service.path("whitelake/Position/").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonPosition);

        return retValue.toString();
    }
    
    public ArrayList<Position> getPositions() throws Exception {
        ArrayList<Position> positions = null;
        String pointAsJson = null;
        
        ClientResponse response;
        try {
            response = service.path("whitelake/Position/")
                    .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            Gson gson = new Gson();
            pointAsJson = response.getEntity(String.class);
            Type collectionType = new TypeToken<ArrayList<Position>>() {
                }.getType();
            positions = gson.fromJson(pointAsJson, collectionType);
        } catch (JsonSyntaxException ex) {
            throw new Exception(pointAsJson);
        } catch (UniformInterfaceException ex) {
            throw new Exception(ex.getResponse().getEntity(String.class));
        }
        
        return positions;
    }
    
    
}
