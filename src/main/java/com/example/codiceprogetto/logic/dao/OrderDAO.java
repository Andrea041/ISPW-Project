package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.DeliveryAddress;
import com.example.codiceprogetto.logic.entities.Order;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;
import com.example.codiceprogetto.logic.utils.TypeConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OrderDAO extends TypeConverter {
    private static final String EMAIL = "email";
    private static final String ORDER_STATUS = "status";
    private static final String PRODUCTS_LIST = "products";
    private static final String ORDER_TOTAL = "total";
    private static final String ORDER_ID = "orderID";
    private static final String ADDRESS = "address";

    private Order generateOrder(ResultSet rs) throws SQLException {
        Order order;
        DeliveryAddress address;
        List<Product> productList;
        OrderStatus orderStatus;

        orderStatus = OrderStatus.fromString( rs.getString(ORDER_STATUS));
        productList = stringConverter(rs.getString(PRODUCTS_LIST));
        address = convertString(rs.getString(ADDRESS));

        order = new Order(rs.getString(EMAIL),
                          address,
                          rs.getDouble(ORDER_TOTAL),
                          rs.getString(ORDER_ID),
                          orderStatus,
                          productList);

        return order;
    }

    private List<Order> newOrderList(ResultSet rs) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        Order order;

        do {
            order = generateOrder(rs);

            orderList.add(order);
        } while(rs.next());

        return orderList;
    }

    public void insertOrder(String email, double total, String orderID, DeliveryAddress address, List<Product> productList) throws DAOException {
        int result;
        OrderStatus orderStatus = OrderStatus.NEW;
        String deliveryAddress;
        String prodList;

        Connection conn = DBConnectionFactory.getConn();

        deliveryAddress = convertAddress(address);
        prodList = listConverter(productList);

        String sql = "INSERT INTO Progetto.Order (email, total, status, address, products, orderID) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, email);
            stmt.setDouble(2, total);
            stmt.setString(3, orderStatus.getId());
            stmt.setString(4, deliveryAddress);
            stmt.setString(5, prodList);
            stmt.setString(6, orderID);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Order creation failed");
        }

        if(result > 0) {
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }
    }

    public Order fetchOrder(String email, String orderStatus) throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        Order order;
        ResultSet rs = null;

        String sql = "SELECT * FROM Progetto.Order WHERE email = ? AND status = ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, email);
            stmt.setString(2, orderStatus);

            rs = stmt.executeQuery();

            if (!rs.first()) {
                return null;
            }

            order = generateOrder(rs);
        } finally {
            if (rs != null)
                rs.close();
        }

        return order;
    }

    public Order fetchOrderByID(String orderID, String orderStatus) throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        Order order;
        ResultSet rs = null;

        String sql = "SELECT * FROM Progetto.Order WHERE orderID = ? AND status = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            stmt.setString(1, orderID);
            stmt.setString(2, orderStatus);

            rs = stmt.executeQuery();

            if (!rs.first()) {
                return null;
            }

            order = generateOrder(rs);
        } finally {
            if (rs != null)
                rs.close();
        }

        return order;
    }

    public List<Order> fetchAllOrder(String orderStatus) throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        List<Order> orderList = new ArrayList<>();
        ResultSet rs = null;

        String sql = "SELECT * FROM Progetto.Order WHERE status = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, orderStatus);

            rs = stmt.executeQuery();

            if (!rs.first()) {
                return orderList;
            }

            rs.first();

            orderList = newOrderList(rs);
        } finally {
            if (rs != null)
                rs.close();
        }

        return orderList;
    }

    public List<Order> fetchAllOrderByEmail(String orderStatus, String email) throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        List<Order> orderList = new ArrayList<>();
        ResultSet rs = null;

        String sql = "SELECT * FROM Progetto.Order WHERE status = ? AND email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, orderStatus);
            stmt.setString(2, email);

            rs = stmt.executeQuery();

            if (!rs.first()) {
                return orderList;
            }

            rs.first();

            orderList = newOrderList(rs);
        } finally {
            if (rs != null)
                rs.close();
        }

        return orderList;
    }

    public void updateOrderStatus(String email, String orderStatus) throws DAOException {
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "UPDATE Progetto.Order SET status = ? WHERE email = ? AND status = 'new'";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, orderStatus);
            stmt.setString(2, email);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Order status update failed");
        }

        if(result > 0) {
            Logger.getAnonymousLogger().log(Level.INFO, "Order status updated correctly");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Order status update error");
        }
    }

    public void updateOrderStatusByID(String orderID, String orderStatus) throws DAOException {
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "UPDATE Progetto.Order SET status = ? WHERE orderID = ? AND status = 'confirmed'";
        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, orderStatus);
            stmt.setString(2, orderID);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in update order status");
        }

        if(result > 0) {
            Logger.getAnonymousLogger().log(Level.INFO, "Order status updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Order status not updated");
        }
    }

    public void deleteOrder(String email) throws DAOException {
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "DELETE FROM Progetto.Order WHERE email = ? AND status = 'new'";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, email);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error in deleting order");
        }

        if(result > 0) {
            Logger.getAnonymousLogger().log(Level.INFO, "Order deleted");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Order not deleted, an error occurred!");
        }
    }
}
