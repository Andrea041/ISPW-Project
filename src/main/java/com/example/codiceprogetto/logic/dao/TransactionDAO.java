package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Transaction;
import com.example.codiceprogetto.logic.enumeration.PaymentType;
import com.example.codiceprogetto.logic.enumeration.TransactionStatus;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDAO {
    public Transaction generateTransaction(ResultSet rs) throws SQLException {
        Transaction transaction;

        transaction = new Transaction(rs.getString("email"),
                                      TransactionStatus.fromString(rs.getString("status")),
                                      rs.getString("transactionID"),
                                      PaymentType.fromString(rs.getString("paymentMethod")));

        return transaction;
    }

    public void insertTransaction(String email, String status, String transactionId, String paymentType) throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        PreparedStatement stmt;
        int result;

        String query = "INSERT INTO Transaction (email, status, transactionID, paymentMethod) VALUES (?, ?, ?, ?)";
        stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setString(2, status);
        stmt.setString(3, transactionId);
        stmt.setString(4, paymentType);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        stmt.close();
    }

    public Transaction fetchTransaction(String transactionID) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        Transaction transaction;
        ResultSet rs;

        String sql = "SELECT * FROM Transaction WHERE transactionID = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, transactionID);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return null;
        }

        rs.first();

        transaction = generateTransaction(rs);

        stmt.close();

        return transaction;
    }
}
