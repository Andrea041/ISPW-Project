package com.example.codiceprogetto.shoppingcart;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ShoppingCartView {
    private Parent root;

    public ShoppingCartView() throws Exception{
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/ShoppingCart/ShoppingCart.fxml"));
    }
    public Parent getShoppingView(){
        return this.root;
    }
}
