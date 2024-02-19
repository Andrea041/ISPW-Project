package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.ShoppingCartApplicativeController;
import com.example.codiceprogetto.logic.bean.CartBean;
import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.NotLoggedUserException;
import com.example.codiceprogetto.logic.observer.Observer;
import com.example.codiceprogetto.logic.utils.Utilities;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShoppingCartGraphicController extends Utilities implements Observer {
    @FXML
    private Label subtotal;
    @FXML
    private Label tax;
    @FXML
    private Label total;
    @FXML
    private VBox productLocation;
    ShoppingCartApplicativeController shop;
    List<ProductStockBean> productStockBeans;

    @FXML
    void initialize() {
        shop = new ShoppingCartApplicativeController();

        updatePriceLabel();
        updateProductGUI();
    }

    public void back() {
        navigateTo(HOME);
    }

    public void accountGUI() {
        try {
            shop.logoutUser();
            navigateTo(HOME);
        } catch (NotLoggedUserException e) {
            alert("You are not logged in!");
        }
    }

    public void cartGUI() {
        navigateTo(CART);
    }

    public void gotoCheckoutGUI() {
        if(shop.checkLogin()) {
            productStockBeans = fetchCartContent();

            if (productStockBeans.isEmpty())
                alert("Your cart is empty!");
            else
                navigateTo(CHECKOUT);
        } else
            alert("To buy items you have to log in or create a new account!");
    }

    public void updatePriceLabel() {
        CartBean price = new CartBean();

        try {
            price = shop.calculatePrice(price);
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "Unknown error in DB");
        } catch(DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        subtotal.setText(round(price.getSubtotal(), 2) + "€");
        total.setText(round(price.getTotal(),2) + "€");
        tax.setText(round(price.getTax(),2) + "€");
    }

    public void updateProductGUI() {
        productStockBeans = fetchCartContent();
        productLocation.getChildren().clear();

        if(!productStockBeans.isEmpty()) {
            for(ProductStockBean prod : productStockBeans)
                appendToCart(prod.getLabelID());
        }
    }

    private void appendToCart(String prodID) {
        Parent root = null;

        ProdInCartGraphicController prodController = new ProdInCartGraphicController(prodID);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/codiceprogetto/FXML/ProdInCart/CartProd.fxml"));
        fxmlLoader.setController(prodController);

        prodController.attach(this);

        try {
            root = fxmlLoader.load();
        } catch (Exception exception) {
            Logger.getAnonymousLogger().log(Level.INFO, "Invalid page");
        }

        productLocation.getChildren().add(root);
    }

    public List<ProductStockBean> fetchCartContent() {
        List<ProductStockBean> productStockBeanList = new ArrayList<>();

        try {
            productStockBeanList = shop.retrieveCartProd();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        } catch (DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        return productStockBeanList;
    }

    public void login() {
        navigateTo(LOGIN);
    }

    @Override
    public void update() {
        initialize();
    }


}
