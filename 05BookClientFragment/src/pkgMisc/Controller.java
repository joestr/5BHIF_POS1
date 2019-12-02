/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMisc;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.impl.MultiPartWriter;
import java.net.URI;
import javax.ws.rs.Consumes;
import static javax.ws.rs.HttpMethod.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import pkgData.Book;

/**
 *
 * @author admin
 */
public class Controller {

    private final static String BOOK_RESOURCE = "book/";
    private String uri = null;
    private ClientConfig config = null;
    private Client client = null;
    private WebResource service = null;

    public void setUri(String uri) throws Exception {
        this.uri = uri;
        config = new DefaultClientConfig();
        config.getClasses().add(MultiPartWriter.class); //concerning image

        client = Client.create(config);
        service = client.resource(getBaseURI());
    }

    public Book getBook(String id) throws Exception {
        Book retBook = null;
        String retBookAsJson = null;
        ClientResponse response;
        try {
            response = service.path(BOOK_RESOURCE + id)
                    .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            Gson gson = new Gson();
            retBookAsJson = response.getEntity(String.class);
            retBook = gson.fromJson(retBookAsJson, Book.class);
        } catch (JsonSyntaxException ex) {
            throw new Exception(retBookAsJson);
        } catch (UniformInterfaceException ex) {
            throw new Exception(ex.getResponse().getEntity(String.class));
        }
        return retBook;
    }

    public String addBook(Book book) throws Exception {
        ClientResponse retValue;
        Gson gson = new Gson();
        String jsonBook = gson.toJson(book, Book.class);
        retValue = service.path(BOOK_RESOURCE).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, jsonBook);

        return retValue.toString();
    }

    public String updateBook(Book book) {
        ClientResponse retValue;
        Gson gson = new Gson();
        String jsonBook = gson.toJson(book, Book.class);
        retValue = service.path(BOOK_RESOURCE).type(MediaType.APPLICATION_JSON).put(ClientResponse.class, jsonBook);

        return retValue.toString();
    }

    private URI getBaseURI() {
        return UriBuilder.fromUri(uri).build();
    }

    public String deleteBook(int parseInt) throws Exception {
        ClientResponse response;

        response = service.path(BOOK_RESOURCE)
                .queryParam("bookid", String.valueOf(parseInt))
                .delete(ClientResponse.class);

        return response.toString() + "{state: " + response.getStatus() + "}";
    }

}
