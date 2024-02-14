package com.example.codiceprogetto.logic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class PaymentSummaryView {
    private final Parent root;
    public PaymentSummaryView() throws Exception {
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/Payment/PaymentSummary.fxml"));
    }
    public Parent getPaymentSummaryView(){
        return this.root;
    }
}
