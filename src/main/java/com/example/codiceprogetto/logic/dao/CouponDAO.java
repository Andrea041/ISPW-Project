package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CouponDAO {
    public int checkCode(String code) throws SQLException, DAOException {
        Connection conn = DBConnectionFactory.getConn();
        ResultSet rs = null;
        int discount = 0;

        String sql = "SELECT discount FROM Coupon WHERE " + "idCoupon" + "= ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, code);

            rs = stmt.executeQuery();

            if (!rs.first()) {
                throw new DAOException("Coupon is not valid!");
            }

            rs.first();

            discount = rs.getInt("discount");
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } finally {
            if (rs != null)
                rs.close();
        }
        
        return discount;
    }
}
