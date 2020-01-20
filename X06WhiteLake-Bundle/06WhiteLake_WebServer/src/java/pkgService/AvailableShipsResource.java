/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgService;

import com.google.gson.Gson;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pkgData.Database;

/**
 * REST Web Service
 *
 * @author admin
 */
@Path("availableShips")
public class AvailableShipsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AvailableShipsResource
     */
    public AvailableShipsResource() {
    }

    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getAvailableShipnames() throws Exception {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        try {
            ArrayList<String> availableShips = Database.getInstance().getAvailableShips();
            response.entity(new Gson().toJson(availableShips));
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR]  " + e.getMessage());
        }
        System.out.println("=== =====webservice GET called");
        return response.build();
    }
}
