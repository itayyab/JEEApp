/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tayyab;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author Tayyab
 */
public class DbConnection {
    String database = "jersey_dbx";
    public Connection getConnection() {
        //try (InputStream input =  ClassLoader.getSystemResourceAsStream("app.properties")) {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties")) {
            //  try (InputStream input =  ClassLoader.getSystemClassLoader().getResourceAsStream("app.properties")) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            // get the property value and print it out
            database = prop.getProperty("db.database");
            String connectionURL = "jdbc:mysql://localhost:3308/" + database;
            Connection connection = null;
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(connectionURL, "root", "root");
            return connection;
        } catch (Exception ex) {
            //System.out.println(String.valueOf(getClass().getResourceAsStream("resources/app.properties")));
           // ex.printStackTrace();
            return null;
        }
    }

}
