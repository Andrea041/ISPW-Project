package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentDAO {
    public void insertPaymentMethod(String email, String name, String lastName, String expiration, String cardNumber, String cvv, String zipCode) throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        PreparedStatement stmt;
        int result;

        String query = "INSERT INTO Payment (email, name, lastName, expiration, cardNumber, cvv, zipCode) VALUES (?, ?, ?, ?, ?, ?, ?) ";
        stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setString(2, name);
        stmt.setString(3, lastName);
        stmt.setString(4, expiration);
        stmt.setString(5, cardNumber);
        stmt.setString(6, cvv);
        stmt.setString(7, zipCode);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        stmt.close();
    }
}
