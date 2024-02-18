package com.example.codiceprogetto;

import com.example.codiceprogetto.logic.grapchiccontroller_cli.HomeGraphicControllerCLI;

public class AppStarterCLI {
    public static void main (String[] args) {
        HomeGraphicControllerCLI homeCLI = new HomeGraphicControllerCLI();
        homeCLI.start();
    }
}
