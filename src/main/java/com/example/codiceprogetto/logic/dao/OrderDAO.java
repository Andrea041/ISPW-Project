package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.DeliveryAddress;
import com.example.codiceprogetto.logic.entities.Order;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;
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
    protected Order generateOrder(ResultSet rs) throws SQLException {
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

    protected List<Order> newOrderList(ResultSet rs) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        Order order;

        do {
            order = generateOrder(rs);

            orderList.add(order);
        } while(rs.next());

        return orderList;
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

    public Order fetchOrder(String email, String orderStatus) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        Order order;
        ResultSet rs;

        String sql = "SELECT * FROM Progetto.Order WHERE email = ? AND status = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setString(2, orderStatus);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return null;
        }

        rs.first();

        order = generateOrder(rs);

        stmt.close();

        return order;
    }

    public Order fetchOrderByID(String orderID, String orderStatus) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        Order order;
        ResultSet rs;

        String sql = "SELECT * FROM Progetto.Order WHERE orderID = ? AND status = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, orderID);
        stmt.setString(2, orderStatus);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return null;
        }

        rs.first();

        order = generateOrder(rs);

        stmt.close();

        return order;
    }

    public List<Order> fetchAllOrder(String orderStatus) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        List<Order> orderList = new ArrayList<>();
        ResultSet rs;

        String sql = "SELECT * FROM Progetto.Order WHERE status = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, orderStatus);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return orderList;
        }

        rs.first();

         orderList = newOrderList(rs);

         stmt.close();

         return orderList;
    }

    public void updateOrderStatus(String email, String orderStatus) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "UPDATE Progetto.Order SET status = ? WHERE email = ? AND status = 'new'";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, orderStatus);
        stmt.setString(2, email);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Order status updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Order status update failed");
        }

        stmt.close();
    }

    public void updateOrderStatusByID(String orderID, String orderStatus) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "UPDATE Progetto.Order SET status = ? WHERE orderID = ? AND status = 'confirmed'";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, orderStatus);
        stmt.setString(2, orderID);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Order status updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Order status update failed");
        }

        stmt.close();
    }

    public void deleteOrder(String email) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "DELETE FROM Progetto.Order WHERE email = ? AND status = 'new'";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Order deleted");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Order not deleted, an error occurred!");
        }

        stmt.close();
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
                                   elements[6]
        );
    }

    private String listConverter(List<Product> list) {
        StringBuilder stringBuilder = new StringBuilder();

        if(list == null)
            return null;

        for(Product product : list) {
            stringBuilder.append(product.getName()).append(",").append(product.getId()).append(",").append(product.getSelectedUnits()).append(",")
                    .append(product.getSize()).append(",").append(product.getPrice()).append(",").append(product.getImage()).append(",");
        }
        return stringBuilder.toString();
    }
    private List<Product> stringConverter(String list) {
        List<Product> listProd = new ArrayList<>();
        String[] elements = list.split(",");

        for(int i = 0; i < elements.length; i += 6) {
            String name = elements[i];
            String id = elements[i + 1];
            int selectedUnits = Integer.parseInt(elements[i + 2]);
            String size = elements[i + 3];
            double price = Double.parseDouble(elements[i + 4]);
            String image = elements[i + 5];

            listProd.add(new Product(name, id, selectedUnits, size, price, image));
        }
        return listProd;
    }
}
