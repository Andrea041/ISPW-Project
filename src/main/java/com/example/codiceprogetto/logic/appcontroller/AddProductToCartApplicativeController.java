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

        ret = new CartDAO().updateCart(product, SessionUser.getInstance().getThisUser().getEmail(), "ADD");

        res = new ProductDAO().updateProductStock(product.getId(), product.getSelectedUnits());
        if(res != 0)
            Logger.getAnonymousLogger().log(Level.INFO, "Product stock updated");
        else
            return res;

        updateTotal();

        return ret;
    }

    public void updateTotal() throws DAOException, SQLException {
        List<Product> cartContent;
        double total = 0;
        String totalStr;

        cartContent = new CartDAO().retrieveCartContent(SessionUser.getInstance().getThisUser().getEmail());
        if(cartContent == null)
            return;

        for(Product prod : cartContent) {
            total += (prod.getPrice() * prod.getSelectedUnits());
        }

        totalStr = String.valueOf(total);

        new CartDAO().updateCartTotal(totalStr, SessionUser.getInstance().getThisUser().getEmail());
    }
}
