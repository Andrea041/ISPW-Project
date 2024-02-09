package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.ProductBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.dao.ProductDAO;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.observer.Observer;
import com.example.codiceprogetto.logic.observer.Subject;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddProductToCartApplicativeController {
    public int updateCart(ProductBean prod) throws SQLException, TooManyUnitsExcpetion, DAOException {
        Product product;
        int ret = -1;
        int res;

        product = new ProductDAO().retrieveProduct(prod.getId(), prod.getUnitsToOrder(), prod.getSize());
        if(product != null)
            Logger.getAnonymousLogger().log(Level.INFO, "Product retrieved from DB");
        else
            return ret;

        try {
            ret = new CartDAO().updateCart(product, SessionUser.getInstance().getThisUser().getEmail(), "ADD");
        } catch (TooManyUnitsExcpetion e) {
            throw new TooManyUnitsExcpetion(e.getMessage());
        }

        res = new ProductDAO().updateProductStock(product.getId(), product.getSelectedUnits());
        if(res != 0)
            Logger.getAnonymousLogger().log(Level.INFO, "Product stock updated");
        else
            return -1;

        updateTotal();

        return ret;
    }

    public void updateTotal() {
        List<Product> cartContent;
        double total = 0;
        String totalStr;

        try {
            cartContent = new CartDAO().retrieveCartContent(SessionUser.getInstance().getThisUser().getEmail());
            if(cartContent == null)
                return;

            for(Product prod : cartContent) {
                total += (prod.getPrice() * prod.getSelectedUnits());
            }

            totalStr = String.valueOf(total);

            new CartDAO().updateCartTotal(totalStr, SessionUser.getInstance().getThisUser().getEmail());
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        } catch (DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }
}
