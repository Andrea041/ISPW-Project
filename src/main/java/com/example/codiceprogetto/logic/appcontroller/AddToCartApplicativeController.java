package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.ProductBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.dao.ProductDAO;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddToCartApplicativeController {
    public int updateCart(ProductBean prod) throws SQLException, TooManyUnitsExcpetion {
        Product product;
        int ret = -1;
        int res;

        product = new ProductDAO().retrieveProduct(prod.getId(), prod.getUnitsToOrder(), prod.getSize());
        if(product != null)
            Logger.getAnonymousLogger().log(Level.INFO, "Product retrieved from DB");
        else
            return ret;

        try {
            ret = new CartDAO().updateCart(product, SessionUser.getInstance().getThisUser().getEmail());
        } catch (TooManyUnitsExcpetion e) {
            throw new TooManyUnitsExcpetion(e.getMessage());
        }

        res = new ProductDAO().updateProductStock(product.getId(), product.getSelectedUnits());
        if(res == 0)
            return -1;

        return ret;
    }
}
