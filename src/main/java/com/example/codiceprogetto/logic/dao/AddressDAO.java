package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddressDAO {
    public void insertAddress(String email, String name, String surname, String address, String city, String country, String state, String phoneNumber) throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        PreparedStatement stmt;
        int result;

        String query = "INSERT INTO Address (email, addressName, addressSurname, address, city, country, state, phoneNumber) values (?, ?, ?, ?, ?, ?, ? , ?) ";
        stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setString(2, name);
        stmt.setString(3, surname);
        stmt.setString(4, address);
        stmt.setString(5, city);
        stmt.setString(6, country);
        stmt.setString(7, state);
        stmt.setString(8, phoneNumber);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }
    }
}
