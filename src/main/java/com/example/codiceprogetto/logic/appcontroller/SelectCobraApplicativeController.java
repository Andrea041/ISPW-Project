package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.ProductBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.dao.ProductDAO;
import com.example.codiceprogetto.logic.entities.Product;

import java.sql.SQLException;

public class SelectCobraApplicativeController {
    public int updateCart(ProductBean prod) throws SQLException {
        Product product;
        int ret = -1;

        product = new ProductDAO().retrieveProduct(prod.getID());

        ret = new CartDAO().updateCart(product);

        return ret;
    }
}
