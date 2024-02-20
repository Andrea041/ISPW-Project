package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.dao.ProductDAOFactory;
import com.example.codiceprogetto.logic.entities.Cart;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.io.IOException;
import java.sql.SQLException;

public class ProdInCartApplicativeController extends UserTool {
    public ProductStockBean updateUI(String prodID, ProductStockBean cart) throws IOException {
        Product prod;

        prod = new ProductDAOFactory().createProductDAO().fetchProduct(prodID);

        cart.setTotalAmount(prod.getPrice());
        cart.setLabelID(prod.getId());
        cart.setProductName(prod.getName());
        cart.setPrice(prod.getPrice());
        cart.setProdImage(prod.getImage());

        return cart;
    }

    public int displaySelectedUnits(String prodID, String email) throws DAOException, SQLException {
        Cart cart;
        int res = -1;

        cart = fetchCart(email);
        if(cart.getProducts().isEmpty())
            return res;

        for(Product prod : cart.getProducts()) {
            if(prod.getId().equals(prodID))
                return prod.getSelectedUnits();
        }

        return res;
    }

    public int removeProduct(String prodID, String email) throws DAOException, SQLException, TooManyUnitsExcpetion {
        Cart cart;
        int res = -1;
        Product toRemove = null;

        cart = fetchCart(email);
        if(cart.getProducts().isEmpty())
            return res;

        for(Product prod : cart.getProducts()) {
            if(prod.getId().equals(prodID))
                toRemove = prod;
        }

        res = new CartDAO().updateCart(toRemove, email, "REMOVE");

        assert toRemove != null;
        new CartDAO().updateCartTotal(cart.getTotal()-(toRemove.getPrice() * toRemove.getSelectedUnits()), email);

        return res;
    }

    public int changeUnits(String prodID, String op, String email) throws DAOException, SQLException, TooManyUnitsExcpetion {
        Cart cart;
        int res = -1;
        Product modifiedProd = null;
        double total = 0;

        cart = fetchCart(email);
        if(cart.getProducts().isEmpty())
            return res;

        for(Product prod : cart.getProducts()) {
            if(prod.getId().equals(prodID) && op.equals("ADD") && prod.getSelectedUnits() < 10) {
                prod.setSelectedUnits(1);
                modifiedProd = prod;
            }
            else if(prod.getId().equals(prodID) && op.equals("DELETE") && prod.getSelectedUnits() > 1) {
                prod.setSelectedUnits(-1);
                modifiedProd = prod;
            }
            else if(prod.getId().equals(prodID) && (op.equals("ADD") || op.equals("REMOVE")) && (prod.getSelectedUnits() > 10 || prod.getSelectedUnits() < 1))
                throw new TooManyUnitsExcpetion("Limit units for each customer reached, the new units aren't added in the cart");
        }
        
        res = new CartDAO().updateCart(modifiedProd, email, "ADD");

        cart = fetchCart(email);
        if(cart.getProducts().isEmpty())
            return res;

        for(Product prod : cart.getProducts()) {
            total += (prod.getPrice() * prod.getSelectedUnits());
        }

        new CartDAO().updateCartTotal(total, SessionUser.getInstance().getThisUser().getEmail());

        return res;
    }

    private Cart fetchCart(String email) throws DAOException, SQLException {
        Cart cart;

        cart = new CartDAO().retrieveCart(email);

        return cart;
    }
}
