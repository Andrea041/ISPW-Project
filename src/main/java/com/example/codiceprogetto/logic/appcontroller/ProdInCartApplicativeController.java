package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.ProductInCartBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.dao.ProductDAO;
import com.example.codiceprogetto.logic.entities.Cart;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdInCartApplicativeController {
    public ProductInCartBean calculateTotalSingleProd(String prodID, ProductInCartBean cart) throws DAOException, SQLException {
        List<Product> cartContent;

        cartContent = fetchCart();
        if(cartContent.isEmpty())
            return null;

        for(Product prod : cartContent) {
            if(prod.getId().equals(prodID)) {
                cart.setTotalAmount(prod.getPrice() * prod.getSelectedUnits());
                cart.setLabelID(prod.getId());
                cart.setProductName(prod.getName());
                cart.setPrice(prod.getPrice());
                cart.setProdImage(prod.getImage());
            }
        }

        return cart;
    }

    public int displaySelectedUnits(String prodID) throws DAOException, SQLException {
        List<Product> cartContent;
        int res = -1;

        cartContent = fetchCart();
        if(cartContent.isEmpty())
            return res;

        for(Product prod : cartContent) {
            if(prod.getId().equals(prodID))
                return prod.getSelectedUnits();
        }

        return res;
    }

    public int removeProduct(String prodID) throws DAOException, SQLException, TooManyUnitsExcpetion {
        List<Product> cartContent;
        int res = -1;
        Product toRemove = null;

        cartContent = fetchCart();
        if(cartContent.isEmpty())
            return res;

        for(Product prod : cartContent) {
            if(prod.getId().equals(prodID))
                toRemove = prod;
            else
                return res;
        }

        res = new CartDAO().updateCart(toRemove, SessionUser.getInstance().getThisUser().getEmail(), "REMOVE");
        new CartDAO().updateCartTotal("0", SessionUser.getInstance().getThisUser().getEmail());

        return res;
    }

    public int changeUnits(String prodID, String op) throws DAOException, SQLException, TooManyUnitsExcpetion {
        List<Product> cartContent;
        int res = -1;
        Product modifiedProd = null;
        double total = 0;
        String totalStr;

        cartContent = fetchCart();
        if(cartContent.isEmpty())
            return res;

        for(Product prod : cartContent) {
            if(prod.getId().equals(prodID) && op.equals("ADD") && prod.getSelectedUnits() < 10) {
                prod.setSelectedUnits(1);
                modifiedProd = prod;
                res = new ProductDAO().updateProductStock(prod.getId(), prod.getSelectedUnits());
                if(res == -1)
                    Logger.getAnonymousLogger().log(Level.INFO, "Product stock updating error");
            }
            else if(prod.getId().equals(prodID) && op.equals("DELETE") && prod.getSelectedUnits() > 1) {
                prod.setSelectedUnits(-1);
                modifiedProd = prod;
                res = new ProductDAO().updateProductStock(prod.getId(), prod.getSelectedUnits());
                if(res == -1)
                    Logger.getAnonymousLogger().log(Level.INFO, "Product stock updating error");
            }
            else if(prod.getId().equals(prodID) && (op.equals("ADD") || op.equals("REMOVE")) && (prod.getSelectedUnits() > 10 || prod.getSelectedUnits() < 1))
                throw new TooManyUnitsExcpetion("Limit units for each customer reached, the new units aren't added in the cart");
        }
        
        res = new CartDAO().updateCart(modifiedProd, SessionUser.getInstance().getThisUser().getEmail(), "ADD");

        cartContent = fetchCart();
        if(cartContent.isEmpty())
            return res;

        for(Product prod : cartContent) {
            total += (prod.getPrice() * prod.getSelectedUnits());
        }

        totalStr = String.valueOf(total);

        new CartDAO().updateCartTotal(totalStr, SessionUser.getInstance().getThisUser().getEmail());

        return res;
    }

    public List<Product> fetchCart() throws DAOException, SQLException {
        Cart cart;

        cart = new CartDAO().retrieveCart(SessionUser.getInstance().getThisUser().getEmail());

        return cart.getProducts();
    }
}
