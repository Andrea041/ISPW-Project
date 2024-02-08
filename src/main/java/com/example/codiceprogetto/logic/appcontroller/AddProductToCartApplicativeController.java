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

public class AddProductToCartApplicativeController extends Subject {
    List<Observer> observers = new ArrayList<>();

    public int updateCart(ProductBean prod) throws SQLException, TooManyUnitsExcpetion, DAOException {
        Product product;
        int ret = -1;
        int res;

        ShoppingCartApplicativeController sp = new ShoppingCartApplicativeController();

        attach(sp);

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
        if(res != 0)
            Logger.getAnonymousLogger().log(Level.INFO, "Product stock updated");
        else
            return -1;

        notifyObserver();

        // detach(sp);

        return ret;
    }

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    protected void notifyObserver() {
        for(Observer observer : observers)
            observer.update();
    }
}
