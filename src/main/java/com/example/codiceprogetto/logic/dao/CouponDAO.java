package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CouponDAO {
    public int checkCode(String code) throws SQLException, DAOException {
        Connection conn = DBConnectionFactory.getConn();
        ResultSet rs;
        PreparedStatement stmt;
        int discount;

        String sql = "SELECT discount FROM Coupon WHERE " + "idCoupon" + "= ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, code);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            throw new DAOException("Coupon is not valid!");
        }

        rs.first();

        discount = rs.getInt("discount");

        stmt.close();
        rs.close();

        return discount;
    }
}
