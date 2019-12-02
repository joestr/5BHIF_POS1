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
import pkgData.Book;
import pkgData.Database;

/**
 * REST Web Service
 *
 * @author admin
 */
@Path("book")
public class BookResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of BookResource
     */
    public BookResource() {
    }

    /**
     * Retrieves representation of an instance of pkgService.BookResource
     *
     * @param id
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{bookid}")
    public Response getBook(@PathParam("bookid") String id) {
        Database db = Database.newInstance();
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        Book retBook = null;
        try {
            retBook = db.getBook(Integer.parseInt(id));
            response.entity(retBook);
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR]  " + e.getMessage());
        }
        System.out.println("=======webservice GET called with " + new Gson().toJson(retBook));
        return response.build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response newBook(String strBook) throws Exception {
        Database db = Database.newInstance();
        Response.ResponseBuilder response = Response.status(Response.Status.CREATED);
        try {
            Book book = new Gson().fromJson(strBook, Book.class);
            db.setBook(book);
            response.entity("book added");
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR]  " + e.getMessage());
        }
        System.out.println("=== =====webservice POST called with " + strBook);
        return response.build();
    }

    /**
     * PUT method for updating or creating an instance of BookResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateBook(String strBook) throws Exception {
        Database db = Database.newInstance();
        Response.ResponseBuilder response = Response.status(Response.Status.OK);
        try {
            Book _book = new Gson().fromJson(strBook, Book.class);
            Book book = db.getBook(_book.getId());
            book.setAuthor(_book.getAuthor());
            book.setTitle(_book.getTitle());
            response.entity("book updated");
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            response.entity("[ERROR] " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("========webservice PUT called with " + strBook);

        return response.build();
    }

    @DELETE
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteBook(@QueryParam("bookid") String id) throws Exception {
        Database db = Database.newInstance();
        Response.ResponseBuilder response = Response.status(Response.Status.NO_CONTENT);
        try {
            db.deleteBook(Integer.valueOf(id));
        } catch (Exception e) {
            response.status(Response.Status.BAD_REQUEST);
            e.printStackTrace();
        }

        System.out.println("========webservice DELETE called with id " + id);

        return response.build();
    }
}
