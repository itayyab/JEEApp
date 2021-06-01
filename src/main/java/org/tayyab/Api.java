package org.tayyab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Root resource (exposed at "myresource" path)
 */

@Path("api")
public class Api {
    DbConnection dbConnection=new DbConnection();
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
    @Produces(MediaType.APPLICATION_JSON)
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
        Response response = databaseOperations.SavePersonData(dbConnection,user);
        return response;
    }

    @POST()
    @Path("/deletedata")
    @Consumes(MediaType.APPLICATION_JSON)// specifies the request body content
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePerson(String jsondata) {
        String res = "Error";
        ObjectMapper mapper = new ObjectMapper();

//JSON from String to Object
        PersonData user = null;
        try {
            user = mapper.readValue(jsondata, PersonData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Response response = databaseOperations.DeletePersonData(dbConnection,String.valueOf(user.getId()));
        return response;
    }
    @GET()
    @Path("/getdata")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData() {
        try {
            List<PersonData> data=databaseOperations.GetPersonsData(dbConnection);
            Gson jsonConverter = new GsonBuilder().create();
            System.out.println(data);
            return Response.status(Response.Status.OK).entity(jsonConverter.toJson(data)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).type(e.getMessage()).build();
            //  e.printStackTrace();
        }
        // return null;
    }


}
