package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.ShoppingCartApplicativeController;
import com.example.codiceprogetto.logic.bean.PriceBean;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.GraphicTool;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShoppingCartGraphicController extends GraphicTool {
    @FXML
    private Label subtotal;
    @FXML
    private Label tax;
    @FXML
    private Label total;
    @FXML
    private VBox productLocation;

    @FXML
    void initialize() {
        updatePriceLabel();
        updateProductGUI();
    }

    public void back(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "HOME");
    }

    public void accountGUI(MouseEvent mouseEvent) {
    }

    public void cartGUI(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "CART");
    }

    public void gotoCheckoutGUI(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "CHECKOUT");
    }

    public void updatePriceLabel() {
        ShoppingCartApplicativeController shop = new ShoppingCartApplicativeController();
        PriceBean price = new PriceBean(0, 0,0);

        try {
            price = shop.calculatePrice(price);
            if(price == null)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error in DB");
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "Unknown error in DB");
        } catch(DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        subtotal.setText(round(price.getSubtotal(),2) + "€");
        total.setText(round(price.getTotal(),2) + "€");
        tax.setText(round(price.getTax(),2) + "€");
    }

    public void updateProductGUI() {
        ShoppingCartApplicativeController shop = new ShoppingCartApplicativeController();
        List<String> nameList = new ArrayList<>();

        try {
            nameList = shop.retrieveCartProd();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        } catch (DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        for(String name : nameList) {
            appendToCart(name, productLocation);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
