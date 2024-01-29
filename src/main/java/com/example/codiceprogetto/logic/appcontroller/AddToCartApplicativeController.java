package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.ProductBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.dao.ProductDAO;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddToCartApplicativeController {
    public int updateCart(ProductBean prod) throws SQLException {
        Product product;
        int ret;

        product = new ProductDAO().retrieveProduct(prod.getId());
        if(product != null)
            Logger.getAnonymousLogger().log(Level.INFO, "Product retrieved from DB");
        // TODO: aggiornamento nelle quantità rimanenti e check per verificare se ne prendo più delle rimanenti

        // TODO: implementare il pattern observer che controlla quando viene aggiunto un prodotto nel carrello per aggiornarlo
        ret = new CartDAO().updateCart(product, SessionUser.getInstance().getThisUser().getEmail());

        return ret;
    }
}
