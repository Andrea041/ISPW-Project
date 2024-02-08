package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.PriceBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.observer.Observer;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShoppingCartApplicativeController implements Observer {
    public PriceBean calculatePrice(PriceBean price) throws SQLException, DAOException {
        double amount;

        amount = new CartDAO().retrieveCartTotal(SessionUser.getInstance().getThisUser().getEmail());

        price.setTotal(amount);
        price.setTax((amount/100)*22);
        price.setSubtotal(price.getTotal() - price.getTax());

        return price;
    }

    public void retriveCartProd() {

    }

    @Override
    public void update() {
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
