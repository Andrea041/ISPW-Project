package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.utils.GraphicTool;
import javafx.scene.input.MouseEvent;

public class BrowseAccessoriesGraphicController extends GraphicTool{
    public void backHomePage(MouseEvent mouseEvent) {
        GraphicTool.navigateTo(mouseEvent, "HOME");
    }

    public void accountGUI(MouseEvent mouseEvent){
        System.out.println("try");
    }

    public void cartGUI(MouseEvent mouseEvent) {
        GraphicTool.navigateTo(mouseEvent, "CART");
    }

    public void selectProduct(MouseEvent mouseEvent) {
        GraphicTool.navigateTo(mouseEvent, "COBRA");
    }
}
