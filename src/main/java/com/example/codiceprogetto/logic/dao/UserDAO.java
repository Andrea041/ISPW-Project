package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.User;
import com.example.codiceprogetto.logic.utils.DBsingleton;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    protected User newUser(ResultSet rs) throws SQLException {
        User user = null;

        user = new User(rs.getString("email"), rs.getString("password"));

        return user;
    }
    public int insertUser(String email, String password) throws SQLException {
        int result = -1;
        PreparedStatement stmt = null;
        Connection conn = DBsingleton.getDBSingletonInstance().getConn();

        String sql = "INSERT INTO Customer (email, password) VALUES (?, ?)";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setString(2, password);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New customer in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Customer insertion failed");
        }

        stmt.close();

        return result;
    }

    public User findUser(String email) throws SQLException {
        Connection conn = DBsingleton.getDBSingletonInstance().getConn();
        ResultSet rs = null;
        User user = null;
        PreparedStatement stmt = null;

        String sql = "SELECT * FROM Customer WHERE " + "email" + " = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return null;
        }

        rs.first();

        user = newUser(rs);

        stmt.close();
        rs.close();

        return user;
    }
}
