package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.dao.ProductDAO;
import com.example.codiceprogetto.logic.entities.Cart;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdInCartApplicativeController {
    public ProductStockBean updateUI(String prodID, ProductStockBean cart) throws DAOException, SQLException {
        Product prod;

        prod = new ProductDAO().fetchProduct(prodID);

        cart.setTotalAmount(prod.getPrice());
        cart.setLabelID(prod.getId());
        cart.setProductName(prod.getName());
        cart.setPrice(prod.getPrice());
        cart.setProdImage(prod.getImage());

        return cart;
    }

    public int displaySelectedUnits(String prodID) throws DAOException, SQLException {
        Cart cart;
        int res = -1;

        cart = fetchCart();
        if(cart.getProducts().isEmpty())
            return res;

        for(Product prod : cart.getProducts()) {
            if(prod.getId().equals(prodID))
                return prod.getSelectedUnits();
        }

        return res;
    }

    public int removeProduct(String prodID) throws DAOException, SQLException, TooManyUnitsExcpetion {
        Cart cart;
        int res = -1;
        Product toRemove = null;

        cart = fetchCart();
        if(cart.getProducts().isEmpty())
            return res;

        for(Product prod : cart.getProducts()) {
            if(prod.getId().equals(prodID))
                toRemove = prod;
        }

        res = new CartDAO().updateCart(toRemove, SessionUser.getInstance().getThisUser().getEmail(), "REMOVE");

        assert toRemove != null;
        new CartDAO().updateCartTotal(cart.getTotal()-(toRemove.getPrice() * toRemove.getSelectedUnits()), SessionUser.getInstance().getThisUser().getEmail());

        return res;
    }

    public int changeUnits(String prodID, String op) throws DAOException, SQLException, TooManyUnitsExcpetion {
        Cart cart;
        int res = -1;
        Product modifiedProd = null;
        double total = 0;

        cart = fetchCart();
        if(cart.getProducts().isEmpty())
            return res;

        for(Product prod : cart.getProducts()) {
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

        cart = fetchCart();
        if(cart.getProducts().isEmpty())
            return res;

        for(Product prod : cart.getProducts()) {
            total += (prod.getPrice() * prod.getSelectedUnits());
        }

        new CartDAO().updateCartTotal(total, SessionUser.getInstance().getThisUser().getEmail());

        return res;
    }

    public Cart fetchCart() throws DAOException, SQLException {
        Cart cart;

        cart = new CartDAO().retrieveCart(SessionUser.getInstance().getThisUser().getEmail());

        return cart;
    }
}
