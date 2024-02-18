package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.ProductBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.dao.ProductDAOFactory;
import com.example.codiceprogetto.logic.entities.Cart;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddProductToCartApplicativeController extends UserTool {
    public int updateCart(ProductBean prod, String email) throws SQLException, TooManyUnitsExcpetion, DAOException, IOException {
        Product product;
        int ret = -1;

        product = new ProductDAOFactory().createProductDAO().fetchProduct(prod.getId());
        if(product != null)
            Logger.getAnonymousLogger().log(Level.INFO, "Product retrieved from DB");
        else
            return ret;

        product.setSelectedUnits(prod.getUnitsToOrder());
        product.setSize(prod.getSize());

        ret = new CartDAO().updateCart(product, email, "ADD");

        updateTotal(email);

        return ret;
    }

    public void updateTotal(String email) throws DAOException, SQLException {
        double total = 0;
        Cart cart;

        cart = new CartDAO().retrieveCart(email);
        if(cart.getProducts() == null)
            return;

        for(Product prod : cart.getProducts()) {
            total += (prod.getPrice() * prod.getSelectedUnits());
        }

        new CartDAO().updateCartTotal(total, email);
    }
}
