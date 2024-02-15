package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.ShoppingCartApplicativeController;
import com.example.codiceprogetto.logic.bean.CartBean;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.observer.Observer;
import com.example.codiceprogetto.logic.utils.GraphicTool;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShoppingCartGraphicController extends GraphicTool implements Observer {
    @FXML
    private Label subtotal;
    @FXML
    private Label tax;
    @FXML
    private Label total;
    @FXML
    private VBox productLocation;

    ShoppingCartApplicativeController shop = new ShoppingCartApplicativeController();

    @FXML
    void initialize() {
        updatePriceLabel();
        updateProductGUI();
    }

    public void back() {
        navigateTo(HOME);
    }

    public void accountGUI() {
        SessionUser.getInstance().logout();
        navigateTo(LOGIN);
    }

    public void cartGUI() {
        navigateTo(CART);
    }

    public void gotoCheckoutGUI() {
        if(total.getText().equals("0.0€")) {
            alert("Your cart is empty!");
        } else
            navigateTo(CHECKOUT);
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
        CartBean cartContent = new CartBean();

        try {
            cartContent = shop.retrieveCartProd(cartContent);
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        } catch (DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        if(!cartContent.getProductList().isEmpty()) {
            for(Product prod : cartContent.getProductList())
                appendToCart(prod.getId());
        }
    }

    private void appendToCart(String prodID) {
        Parent root = null;

        ProdInCartGraphicController prodController = new ProdInCartGraphicController(prodID);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/codiceprogetto/FXML/ProdInCart/Cobra.fxml"));
        fxmlLoader.setController(prodController);

        prodController.attach(this);

        try {
            root = fxmlLoader.load();
        } catch (Exception exception) {
            Logger.getAnonymousLogger().log(Level.INFO, "Invalid page");
        }

        productLocation.getChildren().add(root);
    }

    @Override
    public void update() {
        updatePriceLabel();
    }
}
