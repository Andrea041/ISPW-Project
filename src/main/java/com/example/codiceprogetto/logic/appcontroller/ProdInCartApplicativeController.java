package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;
import java.util.List;

public class ProdInCartApplicativeController {
    public double calculateTotalSingleProd(String prodName) throws DAOException, SQLException {
        List<Product> cartContent;
        int res = -1;

        cartContent = fetchCart();
        if(cartContent == null)
            return res;

        for(Product prod : cartContent) {
            if(prod.getName().equals(prodName))
                return prod.getPrice() * prod.getSelectedUnits();
        }

        return res;
    }

    public int displaySelectedUnits(String prodName) throws DAOException, SQLException {
        List<Product> cartContent;
        int res = -1;

        cartContent = fetchCart();
        if(cartContent == null)
            return res;

        for(Product prod : cartContent) {
            if(prod.getName().equals(prodName))
                return prod.getSelectedUnits();
        }

        return res;
    }

    public List<Product> fetchCart() throws DAOException, SQLException {
        List<Product> cart;

        cart = new CartDAO().retrieveCartContent(SessionUser.getInstance().getThisUser().getEmail());

        return cart;
    }
}
