package org.tayyab;

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
    private final DatabaseOperations databaseOperations = new DatabaseOperations();
    DbConnection dbConnection = new DbConnection();

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
        try {
            ObjectMapper mapper = new ObjectMapper();
            PersonData user = mapper.readValue(jsondata, PersonData.class);
            return databaseOperations.SavePersonData(dbConnection.getConnection(), user);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).type(e.getMessage()).build();
        }
    }

    @POST()
    @Path("/deletedata")
    @Consumes(MediaType.APPLICATION_JSON)// specifies the request body content
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePerson(String jsondata) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PersonData user = mapper.readValue(jsondata, PersonData.class);
            return databaseOperations.DeletePersonData(dbConnection.getConnection(), String.valueOf(user.getId()));

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).type(e.getMessage()).build();
        }
    }

    @GET()
    @Path("/getdata")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData() {
        try {
            List<PersonData> data = databaseOperations.GetPersonsData(dbConnection.getConnection());
            Gson jsonConverter = new GsonBuilder().create();
            return Response.status(Response.Status.OK).entity(jsonConverter.toJson(data)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).type(e.getMessage()).build();
        }
    }


}
