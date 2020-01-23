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
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
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
@Path("generic")
public class DriversNearCity {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DriversNearCity
     */
    public DriversNearCity() {
    }

    /**
     * Retrieves representation of an instance of pkgService.DriversNearCity
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson(@QueryParam("drivername") String driverName, @QueryParam("radius") int radius) {
        /*Database db = Database.getInstance();
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        try {
            double d = db.getNearestDriver(driverName, radius);
            response.entity(String.valueOf(d));
        } catch (Exception e) {
            response.status(Response.Status.INTERNAL_SERVER_ERROR);
            response.entity("[ERROR]  " + e.getMessage());
        }
        System.out.println("========webservice GET called");
        */
        Response.ResponseBuilder response = Response.status(Response.Status.NOT_IMPLEMENTED);
        return response.build();
    }
}
