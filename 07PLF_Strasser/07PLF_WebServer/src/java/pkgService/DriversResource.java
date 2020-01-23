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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;
import pkg07plf_client.data.DriverPosition;
import pkgData.Database;

/**
 * REST Web Service
 *
 * @author Joel
 */
@Path("drivers")
public class DriversResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DriversResource
     */
    public DriversResource() {
    }

    /**
     * PUT method for updating or creating an instance of DriversResource
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        Database db = Database.getInstance();
        Response.ResponseBuilder response = Response.status(Response.Status.CREATED);
        try {
            DriverPosition position = new Gson().fromJson(content, DriverPosition.class);
            ObjectId generatedId = db.insertDriver(position);
            response.entity(generatedId.toString());
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR]  " + e.getMessage());
        }
        System.out.println("========webservice POST called with " + content);
        return response.build();
    }
}
