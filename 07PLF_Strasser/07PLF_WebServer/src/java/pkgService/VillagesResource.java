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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.types.ObjectId;
import pkg07plf_client.data.VillagePosition;
import pkgData.Database;

/**
 * REST Web Service
 *
 * @author Joel
 */
@Path("villages")
public class VillagesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public VillagesResource() {
    }

    /**
     * PUT method for updating or creating an instance of VillagesResource
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        Database db = Database.getInstance();
        Response.ResponseBuilder response = Response.status(Response.Status.CREATED);
        try {
            VillagePosition position = new Gson().fromJson(content, VillagePosition.class);
            ObjectId generatedId = db.insertVillage(position);
            response.entity(generatedId.toString());
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR]  " + e.getMessage());
        }
        System.out.println("========webservice POST called with " + content);
        return response.build();
    }
}
