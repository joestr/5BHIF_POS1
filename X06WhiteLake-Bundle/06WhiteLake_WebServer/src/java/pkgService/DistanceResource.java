/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgService;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pkgData.Database;
import pkgData.DistanceBetween;
import pkgData.SpatialPosition;

/**
 * REST Web Service
 *
 * @author admin
 */
@Path("distance")
public class DistanceResource {

    @Context
    private UriInfo context;

    private ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    public DistanceResource() {
    }

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public void getDistanceBetween(@Suspended final AsyncResponse asyncResponse, @QueryParam(value = "shipname") final String shipName, @QueryParam(value = "long") final double longitude, @QueryParam(value = "lat") final double latitude) {
        executorService.submit(() -> {
            asyncResponse.resume(doGetDistanceBetween(shipName, longitude, latitude));
        });
    }

    private Response doGetDistanceBetween(@QueryParam("shipname") String shipName, @QueryParam("long") double longitude, @QueryParam("lat") double latitude) {
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        try {
            SpatialPosition targetPosition = new SpatialPosition(longitude, latitude);
            double distanceBetween = Database.getInstance().getDistanceBetween(shipName, targetPosition);
            DistanceBetween distanceObject = new DistanceBetween(shipName, targetPosition, distanceBetween);
            response.entity(new Gson().toJson(distanceObject));
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR]  " + e.getMessage());
        }
        System.out.println("=== =====webservice GET called");
        return response.build();
    }
}
