/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgService;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pkgData.*;

/**
 * REST Web Service
 *
 * @author admin
 */
@Path("Position")
public class WhiteLakeResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BookResource
     */
    public WhiteLakeResource() {
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response newBook(String strPoint) throws Exception {
        Database db = Database.newInstance("127.0.0.1");
        Response.ResponseBuilder response = Response.status(Response.Status.CREATED);
        try {
            Position position = new Gson().fromJson(strPoint, Position.class);
            db.insertRestaurant(position);
            response.entity("Position added");
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR]  " + e.getMessage());
        }
        System.out.println("=== =====webservice POST called with " + strPoint);
        return response.build();
    }
}
