package com.example.codiceprogetto.logic.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBsingleton {
    private static DBsingleton instance = null;
    private Connection conn;
    protected DBsingleton() {
        try{
            Properties prop = new Properties();
            FileInputStream file = new FileInputStream("src/main/java/com/example/codiceprogetto/logic/utils/configuration.properties");

            prop.load(file);
            String url = prop.getProperty("URL");
            String user = prop.getProperty("USER");
            String password = prop.getProperty("PASSWORD");

            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }
   public synchronized static DBsingleton getDBSingletonInstance() {
        if(DBsingleton.instance == null)
            DBsingleton.instance = new DBsingleton();
        return instance;
   }
   public Connection getConn(){
        return conn;
   }
}
