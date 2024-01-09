package com.example.codiceprogetto.queries;

import java.sql.*;

import com.example.codiceprogetto.entities.User;

public class Queries {
    public static ResultSet insertUser(Statement stmt, User user) throws SQLException {
        String sql = "INSERT INTO Customer (email, password) VALUES (" + user.getEmail() +"," + user.getPassword() + ")";
        return stmt.executeQuery(sql) ;
    }
}
