package com.example.codiceprogetto.logic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ShoppingCartView {
    private final Parent root;

    public ShoppingCartView() throws Exception{
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/ShoppingCart/ShoppingCart.fxml"));
    }
    public Parent getShoppingView(){
        return this.root;
    }
}
