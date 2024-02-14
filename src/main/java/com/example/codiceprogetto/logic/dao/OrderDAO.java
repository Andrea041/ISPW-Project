package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.DeliveryAddress;
import com.example.codiceprogetto.logic.entities.Order;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OrderDAO {
    public Order retrieveOrder(ResultSet rs) throws SQLException {
        Order order;
        DeliveryAddress address;
        List<Product> productList;
        OrderStatus orderStatus;

        orderStatus = OrderStatus.fromString( rs.getString("status"));
        productList = stringConverter(rs.getString("products"));
        address = convertString(rs.getString("address"));

        order = new Order(address,
                          rs.getDouble("total"),
                          rs.getString("orderID"),
                          orderStatus,
                          productList);

        return order;
    }

    public void createOrder(String email, double total, String orderID, DeliveryAddress address, List<Product> productList) throws SQLException {
        int result;
        OrderStatus orderStatus = OrderStatus.NEW;
        String deliveryAddress;
        String prodList;

        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();

        deliveryAddress = convertAddress(address);
        prodList = listConverter(productList);

        String sql = "INSERT INTO Progetto.Order (email, total, status, address, products, orderID) VALUES (?, ?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setDouble(2, total);
        stmt.setString(3, orderStatus.getId());
        stmt.setString(4, deliveryAddress);
        stmt.setString(5, prodList);
        stmt.setString(6, orderID);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        stmt.close();
    }

    public String checkOrderStatus(String email) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        String status;
        ResultSet rs;

        String sql = "SELECT status FROM Progetto.Order WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return null;
        }

        rs.first();

        status = rs.getString("status");

        stmt.close();

        return status;
    }

    public Order fetchNewOrder(String email) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        Order order;
        ResultSet rs;

        String sql = "SELECT * FROM Progetto.Order WHERE email = ? AND status = 'new'";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return null;
        }

        rs.first();

        order = retrieveOrder(rs);

        stmt.close();

        return order;
    }

    private String convertAddress(DeliveryAddress address) {
        return address.getName() + "," +
                address.getSurname() + "," +
                address.getAddress() + "," +
                address.getCity() + "," +
                address.getCountry() + "," +
                address.getState() + "," +
                address.getPhoneNumber();
    }

    private DeliveryAddress convertString(String address) {
        String[] elements = address.split(",");

        return new DeliveryAddress(elements[0],
                                   elements[1],
                                   elements[2],
                                   elements[3],
                                   elements[4],
                                   elements[5],
                                   elements[6]);
    }

    private String listConverter(List<Product> list) {
        StringBuilder stringBuilder = new StringBuilder();

        if(list == null)
            return null;

        for(Product product : list) {
            stringBuilder.append(product.getName()).append(",").append(product.getId()).append(",").append(product.getSelectedUnits()).append(",")
                    .append(product.getSize()).append(",").append(product.getPrice()).append(",");
        }
        return stringBuilder.toString();
    }
    private List<Product> stringConverter(String list) {
        List<Product> listProd = new ArrayList<>();
        String[] elements = list.split(",");

        for(int i = 0; i < elements.length; i += 5) {
            String name = elements[i];
            String id = elements[i + 1];
            int selectedUnits = Integer.parseInt(elements[i + 2]);
            String size = elements[i + 3];
            double price = Double.parseDouble(elements[i + 4]);

            listProd.add(new Product(name, id, selectedUnits, size, price));
        }
        return listProd;
    }
}
