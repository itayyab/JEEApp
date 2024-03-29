package org.tayyab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    List<PersonData> GetPersonsData(Connection conn) throws Exception {
        Statement  st = conn.createStatement();
        List<PersonData> data = new ArrayList<PersonData>();
        try {
            String query = "SELECT * FROM persondata";
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            // iterate through the java resultset
            while (rs.next()) {
                int id = rs.getInt("PID");
                String firstName = rs.getString("PName");
                PersonData pd = new PersonData();
                pd.setId(id);
                pd.setName(firstName);
                data.add(pd);
            }
        } catch (Exception e) {
            throw (e);
        } finally {
                st.close();
            conn.close();
        }
        return data;
    }

    Response SavePersonData(Connection conn, PersonData user) throws Exception {
        String res = "Error";
        Response response = null;
        ResultSet rs = null;
        String sql = "insert into persondata (PName) values(?)";
        PreparedStatement  pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int count = 0;
        Gson jsonConverter = new GsonBuilder().create();
        try {
            pst.setString(1, user.getName());
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            rs.next();
            count = rs.getInt(1);
            res = "Data saved Successfully";
            String resp = "{\"id\":" + count + ",\"msg\":\"" + res + "\"}";
            response = Response.status(Response.Status.OK)
                    .entity(jsonConverter.toJson(resp))
                    .type(MediaType.APPLICATION_JSON
                    ).build();

        } catch (Exception e) {
            String resp = "{\"id\":" + count + ",\"msg\":\"" + e.getMessage() + "\"}";
            jsonConverter.toJson(resp);
        } finally {
                pst.close();
                rs.close();
                conn.close();
        }

        return response;
    }

    Response DeletePersonData(Connection conn, String user) throws SQLException {
        String res="";
        String sql = "delete from persondata where PID=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        try {
            pst.setString(1, user);
            pst.executeUpdate();
            res = "Data deleted Successfully";
        } catch (Exception e) {
            res = e.getMessage();
        } finally {
                pst.close();
            conn.close();
        }
        return Response.status(Response.Status.OK)
                .entity(res)
                .type(MediaType.APPLICATION_JSON).build();
    }
}