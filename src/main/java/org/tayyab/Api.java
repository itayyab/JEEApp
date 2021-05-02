package org.tayyab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("api")
public class Api {

    private final DatabaseOperations databaseOperations = new DatabaseOperations();

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */

    @POST()
    @Path("/adddata")
    @Consumes(MediaType.APPLICATION_JSON)// specifies the request body content
    @Produces(MediaType.TEXT_PLAIN)
    public Response addPerson(String jsondata) {
        String res = "Error";
        ObjectMapper mapper = new ObjectMapper();

//JSON from String to Object
        PersonData user = null;
        try {
            user = mapper.readValue(jsondata, PersonData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Response response = databaseOperations.SavePersonData(user);
        return response;
    }


    @GET()
    @Path("/getdata")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData() {
        try {
            List<PersonData> data=databaseOperations.GetPersonsData();
            Gson jsonConverter = new GsonBuilder().create();
            return Response.status(Response.Status.OK).entity(jsonConverter.toJson(data)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).type(e.getMessage()).build();
            //  e.printStackTrace();
        }
        // return null;
    }


}
