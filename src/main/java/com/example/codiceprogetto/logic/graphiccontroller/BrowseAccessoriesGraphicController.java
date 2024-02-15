package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.utils.GraphicTool;

public class BrowseAccessoriesGraphicController extends GraphicTool{
    public void backHomePage() {
        navigateTo(HOME);
    }

    public void accountGUI(){
        System.out.println("try");
    }

    public void cartGUI() {
        navigateTo(CART);
    }

    public void selectProduct() {
        navigateTo(COBRA);
    }
}
