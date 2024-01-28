package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.utils.DBsingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO {
    public int insertCustomer(String email, String password, String userType, String name, String surname) throws SQLException {
        int result = -1;
        PreparedStatement stmt = null;
        Connection conn = DBsingleton.getInstance().getConn();

        String sql = "INSERT INTO Customer (email, password, userType, name, surname) VALUES (?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setString(2, password);
        stmt.setString(3, userType);
        stmt.setString(4, name);
        stmt.setString(5, surname);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        stmt.close();

        return result;
    }
}
