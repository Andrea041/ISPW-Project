package com.example.codiceprogetto.logic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class CheckoutView {
    private final Parent root;
    public CheckoutView() throws Exception {
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/Checkout/Checkout.fxml"));
    }
    public Parent getCheckoutView(){
        return this.root;
    }
}
