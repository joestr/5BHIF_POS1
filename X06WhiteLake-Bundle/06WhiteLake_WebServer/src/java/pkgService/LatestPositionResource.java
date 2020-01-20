/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgService;

import com.google.gson.Gson;
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
@Path("latestPosition")
public class LatestPositionResource {

    @Context
    private UriInfo context;

    public LatestPositionResource() {
    }

    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getLatestPositions() throws Exception {
        Response.ResponseBuilder response = Response.status(Response.Status.CREATED);
        try {
            response.entity(new Gson().toJson(Database.getInstance().getLatestPositions()));
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR]  " + e.getMessage());
        }
        System.out.println("========webservice GET called");
        return response.build();
    }
}
