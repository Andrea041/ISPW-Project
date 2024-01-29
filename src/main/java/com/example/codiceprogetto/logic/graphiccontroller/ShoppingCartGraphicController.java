package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.utils.GraphicTool;
import com.example.codiceprogetto.logic.view.HomePageView;
import com.example.codiceprogetto.logic.view.ShoppingCartView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShoppingCartGraphicController extends GraphicTool {
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
