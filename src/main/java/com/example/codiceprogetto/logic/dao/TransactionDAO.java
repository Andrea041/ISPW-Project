package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Transaction;
import com.example.codiceprogetto.logic.enumeration.PaymentType;
import com.example.codiceprogetto.logic.enumeration.TransactionStatus;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDAO {
    private static final String EMAIL = "email";
    private static final String TRANSACTION_STATUS = "status";
    private static final String TRANSACTION_ID = "transactionID";
    private static final String PAYMENT_METHOD = "paymentMethod";

    public Transaction generateTransaction(ResultSet rs) throws SQLException {
        Transaction transaction;

        transaction = new Transaction(rs.getString(EMAIL),
                                      TransactionStatus.fromString(rs.getString(TRANSACTION_STATUS)),
                                      rs.getString(TRANSACTION_ID),
                                      PaymentType.fromString(rs.getString(PAYMENT_METHOD)));

        return transaction;
    }

    public void insertTransaction(String email, String status, String transactionId, String paymentType) {
        Connection conn = DBConnectionFactory.getConn();
        int result = 0;
        String query = "INSERT INTO Transaction (email, status, transactionID, paymentMethod) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, email);
            stmt.setString(2, status);
            stmt.setString(3, transactionId);
            stmt.setString(4, paymentType);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }
    }

    public Transaction fetchTransaction(String transactionID) throws DAOException {
        Connection conn = DBConnectionFactory.getConn();
        Transaction transaction = null;
        ResultSet rs;

        String sql = "SELECT * FROM Transaction WHERE transactionID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, transactionID);

            rs = stmt.executeQuery();

            if (!rs.first()) {
                throw new DAOException("Error in fetching the transaction");
            }

            rs.first();

            transaction = generateTransaction(rs);
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        return transaction;
    }
}
