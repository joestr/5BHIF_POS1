/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htl_villach.edu.strassej._06shipserver.service;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;
import at.htl_villach.edu.strassej._06shipserver.data.Database;
import at.htl_villach.edu.strassej._06shipserver.data.MongoLogPositionEntry;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author admin
 */
@Path("position")
public class Position {

    @Context
    private UriInfo context;

    public Position() {
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response newPosition(String strPosition) throws Exception {
        Database db = Database.getInstance();
        Response.ResponseBuilder response = Response.status(Response.Status.CREATED);
        try {
            MongoLogPositionEntry position = new Gson().fromJson(strPosition, MongoLogPositionEntry.class);
            ObjectId generatedId = db.insertPosition(position);
            response.entity(generatedId.toString());
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR]  " + e.getMessage());
        }
        System.out.println("========webservice POST called with " + strPosition);
        return response.build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPosition() throws Exception {
        Response.ResponseBuilder response = Response.status(Response.Status.CREATED);
        try {
            response.entity("test");
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR]  " + e.getMessage());
        }
        System.out.println("=== =====webservice GET called");
        return response.build();
    }
}
