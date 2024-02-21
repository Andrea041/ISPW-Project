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
    private static final String PRODUCTS = "products";
    private static final String TOTAL_CART = "total";
    private static final String APPLIED_DISCOUNT = "appliedDiscount";
    private static final String SHIP = "shipping";

    private Cart createCartEntity(ResultSet rs, String email) throws SQLException {
        Cart cart;
        List<Product> productList;
        String prodList;

        prodList = rs.getString(PRODUCTS);

        if(prodList != null && !prodList.isEmpty())
            productList = stringConverter(prodList);
        else
            productList = new ArrayList<>();

        cart = new Cart(email, productList, rs.getDouble(TOTAL_CART), rs.getInt(APPLIED_DISCOUNT), rs.getInt(SHIP));

        return cart;
    }

    public void createCustomerCart(String email) throws DAOException {
        int result;
        Connection conn = DBConnectionFactory.getConn();

        String sql = "INSERT INTO Cart (email, products, total) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, email);
            stmt.setString(2, null);
            stmt.setDouble(3, 0);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Unable to create new cart");
        }

        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        }
    }

    public int updateCart(Product product, String email, String op) throws TooManyUnitsExcpetion, DAOException, SQLException {
        int result;
        String listUpdated;
        Cart cart;

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

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, listUpdated);
            stmt.setString(2, email);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Unable to update cart");
        }

        if(result > 0)
            Logger.getAnonymousLogger().log(Level.INFO, "Cart updated");

        return result;
    }

    public Cart retrieveCart(String email) throws SQLException, DAOException {
        Cart cart;
        ResultSet rs = null;
        Connection conn = DBConnectionFactory.getConn();

        String sql = "SELECT * FROM Cart WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, email);

            rs = stmt.executeQuery();

            if (!rs.first())
                throw new DAOException("Not existing cart");

            rs.first();

            cart = createCartEntity(rs, email);
        } finally {
            if (rs != null)
                rs.close();
        }

        return cart;
    }

    public void updateCartCoupon(int coupon, String email) throws DAOException {
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "UPDATE Cart SET appliedDiscount = ? WHERE email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setInt(1, coupon);
            stmt.setString(2, email);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cart coupon discount update failed");
        }

        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Discount updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Discount update failed");
        }
    }

    public void updateCartShipping(int shipping, String email) throws DAOException {
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "UPDATE Cart SET shipping = ? WHERE email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setInt(1, shipping);
            stmt.setString(2, email);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cart shipping amount update failed");
        }

        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Shipping updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Shipping update failed");
        }
    }

    public void updateCartTotal(double totalStr, String email) throws DAOException {
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "UPDATE Cart SET total = ? WHERE email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setDouble(1, totalStr);
            stmt.setString(2, email);

            result = stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Cart total update failed");
        }

        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Total amount updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Total amount update failed");
        }
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
