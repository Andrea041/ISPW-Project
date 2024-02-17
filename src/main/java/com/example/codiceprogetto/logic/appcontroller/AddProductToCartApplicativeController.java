package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.ProductBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.dao.ProductDAOJdbc;
import com.example.codiceprogetto.logic.entities.Cart;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddProductToCartApplicativeController {
    public int updateCart(ProductBean prod) throws SQLException, TooManyUnitsExcpetion, DAOException {
        Product product;
        int ret = -1;
        int res;

        product = new ProductDAOJdbc().fetchProduct(prod.getId());
        if(product != null)
            Logger.getAnonymousLogger().log(Level.INFO, "Product retrieved from DB");
        else
            return ret;

        product.setSelectedUnits(prod.getUnitsToOrder());
        product.setSize(prod.getSize());

        ret = new CartDAO().updateCart(product, SessionUser.getInstance().getThisUser().getEmail(), "ADD");

        updateTotal();

        return ret;
    }

    public void updateTotal() throws DAOException, SQLException {
        double total = 0;
        Cart cart;

        cart = new CartDAO().retrieveCart(SessionUser.getInstance().getThisUser().getEmail());
        if(cart.getProducts() == null)
            return;

        for(Product prod : cart.getProducts()) {
            total += (prod.getPrice() * prod.getSelectedUnits());
        }

        new CartDAO().updateCartTotal(total, SessionUser.getInstance().getThisUser().getEmail());
    }
}
