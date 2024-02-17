package com.example.codiceprogetto.logic.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionFactory {
    private static Connection conn;

    private DBConnectionFactory() {}

    static {
        try (FileInputStream file = new FileInputStream("src/main/java/com/example/codiceprogetto/logic/utils/configuration.properties")) {
            Properties prop = new Properties();
            prop.load(file);

            String url = prop.getProperty("URL");
            String user = prop.getProperty("USER");
            String password = prop.getProperty("PASSWORD");

            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException | IOException e){
            Logger.getAnonymousLogger().log(Level.INFO, "Connection to DB had an error");
        }
    }
   public static Connection getConn() {
        return conn;
   }
}
