/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tayyab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tayyab
 */
public class DbConnection {

    public Connection getConnection() throws Exception {
        try {
            String connectionURL = "jdbc:mysql://localhost:3306/jersey_db";
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(connectionURL, "root", "");
            return connection;
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

}
