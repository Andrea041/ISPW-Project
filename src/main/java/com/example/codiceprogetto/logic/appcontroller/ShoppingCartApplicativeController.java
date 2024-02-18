package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.CartBean;
import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.entities.Cart;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartApplicativeController extends UserTool {
    public CartBean calculatePrice(CartBean price) throws SQLException, DAOException {
        Cart cart;

        cart = new CartDAO().retrieveCart(SessionUser.getInstance().getThisUser().getEmail());

        price.setTotal(cart.getTotal());
        price.setTax((cart.getTotal()/100) * 22);
        price.setSubtotal(cart.getTotal() - price.getTax());

        return price;
    }

    public List<ProductStockBean> retrieveCartProd() throws DAOException, SQLException{
        Cart cart;
        List<ProductStockBean> productListBeans = new ArrayList<>();
        ProductStockBean productStockBean;

        cart = new CartDAO().retrieveCart(SessionUser.getInstance().getThisUser().getEmail());

        for(Product prod : cart.getProducts()) {
            productStockBean = new ProductStockBean(prod.getId(), prod.getImage(), prod.getPrice(), prod.getName(), prod.getSelectedUnits());
            productListBeans.add(productStockBean);
        }

        return productListBeans;
    }
}
