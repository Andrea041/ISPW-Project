package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.utils.GraphicTool;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class ShoppingCartGraphicController extends GraphicTool {
    @FXML
    private Label subtotal;


    public void back(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "HOME");
    }

    public void accountGUI(MouseEvent mouseEvent) {
    }

    public void cartGUI(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "CART");
    }

    public void gotoCheckoutGUI(MouseEvent mouseEvent) {
    }
}
