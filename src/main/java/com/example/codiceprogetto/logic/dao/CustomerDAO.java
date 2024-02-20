package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Customer;
import com.example.codiceprogetto.logic.entities.DeliveryAddress;
import com.example.codiceprogetto.logic.entities.Payment;
import com.example.codiceprogetto.logic.enumeration.UserType;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO extends AbsUserDAO {
    private static final String NAME_ADDRESS = "addressName";
    private static final String SURNAME_ADDRESS = "addressSurname";
    private static final String ADDRESS = "address";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String NAME_PAYMENT = "name";
    private static final String SURNAME_PAYMENT = "lastName";
    private static final String EXPIRATION = "expiration";
    private static final String CARD_NUMBER = "cardNumber";
    private static final String CVV = "cvv";
    private static final String ZIP_CODE = "zipCode";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NAME_CUSTOMER = "name";
    private static final String SURNAME_CUSTOMER = "surname";


    private Customer newCustomer(ResultSet rs) throws SQLException {
        Customer customer;
        DeliveryAddress address;
        Payment payment;

        address = new DeliveryAddress(rs.getString(NAME_ADDRESS),
                                      rs.getString(SURNAME_ADDRESS),
                                      rs.getString(ADDRESS),
                                      rs.getString(CITY),
                                      rs.getString(STATE),
                                      rs.getString(PHONE_NUMBER));
        payment = new Payment(rs.getString(NAME_PAYMENT),
                                        rs.getString(SURNAME_PAYMENT),
                                        rs.getString(EXPIRATION),
                                        rs.getString(CARD_NUMBER),
                                        rs.getString(CVV),
                                        rs.getString(ZIP_CODE));

        customer = new Customer(rs.getString(EMAIL),
                                rs.getString(PASSWORD),
                                UserType.CUSTOMER,
                                rs.getString(NAME_CUSTOMER),
                                rs.getString(SURNAME_CUSTOMER),
                                address,
                                payment);

        return customer;
    }

    public int insertCustomer(String email, String password, String userType, String name, String surname) throws DAOException {
        int result;
        String query = "INSERT INTO Customer (email, password, userType, name, surname) VALUES (?, ?, ?, ?, ?)";

        result = registerUser(email, password, userType, name, surname, query);

        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        return result;
    }

    public Customer findCustomer(String email) throws SQLException, DAOException {
        Connection conn = DBConnectionFactory.getConn();
        ResultSet rs = null;
        Customer customer = null;

        String sql = "SELECT Customer.*, Address.*, Payment.* FROM Customer " +
                "LEFT JOIN Address ON Customer.email = Address.email " +
                "LEFT JOIN Payment ON Customer.email = Payment.email " +
                "WHERE Customer.email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, email);

            rs = stmt.executeQuery();

            if (!rs.first()) {
                throw new DAOException("Error fetching customer");
            }

            rs.first();

            customer = newCustomer(rs);
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } finally {
            if (rs != null)
                rs.close();
        }

        return customer;
    }
}
