package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Cart;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
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

public class CartDAO extends TypeConverter {
    public Cart createCartEntity(ResultSet rs, String email) throws SQLException {
        Cart cart;
        List<Product> productList;
        String prodList;

        prodList = rs.getString("products");

        if(prodList != null && !prodList.isEmpty())
            productList = stringConverter(prodList);
        else
            productList = new ArrayList<>();

        cart = new Cart(email, productList, rs.getDouble("total"), rs.getInt("appliedDiscount"), rs.getInt("shipping"));

        return cart;
    }

    public int createCustomerCart(String email) throws SQLException {
        int result;
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();

        String sql = "INSERT INTO Cart (email, products, total) VALUES (?, ?, ?)";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setString(2, null);
        stmt.setDouble(3, 0);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        stmt.close();

        return result;
    }

    public int updateCart(Product product, String email, String op) throws TooManyUnitsExcpetion, DAOException, SQLException {
        int result;
        String listUpdated;
        Cart cart;

        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();

        cart = retrieveCart(email);

        if(cart.getProducts() == null || cart.getProducts().isEmpty())
            cart.getProducts().add(product);
        else if(op.equals("ADD") && !countUnits(cart.getProducts(), product))
            cart.getProducts().add(product);
        else if(op.equals("REMOVE"))
            cart.getProducts().removeIf(prod -> prod.getName().equals(product.getName()));
        else if(op.equals("REMOVE ALL"))
            cart.getProducts().clear();

        listUpdated = listConverter(cart.getProducts());

        String sql = "UPDATE Cart SET products = ? WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, listUpdated);
        stmt.setString(2, email);

        result = stmt.executeUpdate();
        if(result > 0)
            Logger.getAnonymousLogger().log(Level.INFO, "Cart updated");
        else
            Logger.getAnonymousLogger().log(Level.INFO, "Cart update failed");

        stmt.close();

        return result;
    }

    public Cart retrieveCart(String email) throws SQLException, DAOException {
        Cart cart;

        PreparedStatement stmt;
        ResultSet rs;
        Connection conn = DBConnectionFactory.getConn();

        String sql = "SELECT * FROM Cart WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        rs = stmt.executeQuery();

        if(!rs.first())
            throw new DAOException("Not existing cart");

        rs.first();

        cart = createCartEntity(rs, email);

        rs.close();
        stmt.close();

        return cart;
    }

    public void updateCartCoupon(int coupon, String email) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "UPDATE Cart SET appliedDiscount = ? WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, coupon);
        stmt.setString(2, email);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Discount updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Discount update failed");
        }

        stmt.close();
    }

    public void updateCartShipping(int shipping, String email) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "UPDATE Cart SET shipping = ? WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, shipping);
        stmt.setString(2, email);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Shipping updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Shipping update failed");
        }

        stmt.close();
    }

    public void updateCartTotal(double totalStr, String email) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "UPDATE Cart SET total = ? WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setDouble(1, totalStr);
        stmt.setString(2, email);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Total amount updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Total amount update failed");
        }

        stmt.close();
    }

    private boolean countUnits(List<Product> prodList, Product newProd) throws TooManyUnitsExcpetion {
        for(Product prod : prodList) {
            if(prod.getName().equals(newProd.getName())) {
                prod.setSelectedUnits(newProd.getSelectedUnits() + prod.getSelectedUnits());
                if(prod.getSelectedUnits() <= 10)
                    return true;
                else
                    throw new TooManyUnitsExcpetion("Limit units for each customer reached, the new units aren't added in the cart");
            }
        }
        return false;
    }
}
