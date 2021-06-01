package org.tayyab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    public DatabaseOperations() {
    }

    List<PersonData> GetPersonsData(DbConnection dbConnection) throws Exception {
        List<PersonData> data = new ArrayList<PersonData>();
        Connection conn = dbConnection.getConnection();

        String query = "SELECT * FROM persondata";

        // create the java statement
        Statement st = conn.createStatement();

        // execute the query, and get a java resultset
        ResultSet rs = st.executeQuery(query);

        // iterate through the java resultset
        while (rs.next()) {
            int id = rs.getInt("PID");
            String firstName = rs.getString("PName");

            PersonData pd = new PersonData();
            pd.setId(id);
            pd.setName(firstName);
            // print the results
            data.add(pd);
            // return String.format("%s, %s\n", id, firstName);


        }
        st.close();
        conn.close();
        //  GenericEntity entity = new GenericEntity<List<PersonData>>(data){};
        //Response response = Response.status(Response.Status.OK).entity(entity).type(MediaType.APPLICATION_JSON).build();
        //  GenericEntity<List<PersonData>> entity = new GenericEntity<List<PersonData>>(data){};
        return data;
        //  return Response.status(Response.Status.OK).entity(entity).build();
        //  return Response.ok(entity).build();
        // return response;
    }
    Response SavePersonData(DbConnection dbConnection,PersonData user)  {
        String res="Error";
               Response response=null;
        try {

            String sql = "insert into persondata (PName) values(?)";
            String[] generatedId = { "ID" };
            Connection conn = dbConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, user.getName());

            pst.executeUpdate();

                ResultSet rs = pst.getGeneratedKeys();
                rs.next();
               int count = rs.getInt(1);


            pst.close();
            conn.close();
            Gson jsonConverter = new GsonBuilder().create();
            res = "Data saved Successfully";
            String resp= "{\"id\":"+count+",\"msg\":\""+res+"\"}";
            response=Response.status(Response.Status.OK)
                    .entity(jsonConverter.toJson(resp))
                    .type(MediaType.APPLICATION_JSON
                    ).build();

        } catch (Exception e) {
            e.printStackTrace();
            res = e.getMessage();

        }

        return response;
    }
    Response DeletePersonData(DbConnection dbConnection,String user) {
        String res;
        try {

            String sql = "delete from persondata where PID=?";

            Connection conn = dbConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user);

            pst.executeUpdate();
            pst.close();
            conn.close();
            res = "Data deleted Successfully";
        } catch (Exception e) {
            //e.printStackTrace();
            res = e.getMessage();
        }
        return Response.status(Response.Status.OK)
                .entity(res)
                .type(MediaType.APPLICATION_JSON).build();
       // return res;
    }
}