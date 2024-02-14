package com.example.codiceprogetto.logic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class PaymentView {
    private final Parent root;
    public PaymentView() throws Exception {
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/Payment/Payment.fxml"));
    }
    public Parent getPaymentView(){
        return this.root;
    }
}
