package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import com.example.codiceprogetto.logic.appcontroller.HomePageApplicativeController;
import com.example.codiceprogetto.logic.exception.InvalidFormatException;
import com.example.codiceprogetto.logic.exception.NotLoggedUserException;
import com.example.codiceprogetto.logic.utils.PrinterCLI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeGraphicControllerCLI extends AbsGraphicControllerCLI {
    HomePageApplicativeController homeApp = new HomePageApplicativeController();

    @Override
    public void start() {
        while (true) {
            int choice;
            try {
                choice = showMenu();
                switch(choice) {
                    case 1 -> new LoginGraphicControllerCLI().start();
                    case 2 -> logout();
                    case 3 -> new BrowseAccessoriesGraphicControllerCLI().start();
                    case 4 -> System.exit(0);
                    default -> throw new InvalidFormatException("Invalid choice");
                }
            } catch (InvalidFormatException | IOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        PrinterCLI.printf("--- Bubble Shop ---");
        PrinterCLI.printf("1. Login");
        PrinterCLI.printf("2. Logout");
        PrinterCLI.printf("3. See our accessories");
        PrinterCLI.printf("4. Quit");

        return getMenuChoice(1, 4);
    }

    public void logout() {
        try {
            homeApp.logoutUser();
            Logger.getAnonymousLogger().log(Level.INFO, "Logout performed");
        } catch (NotLoggedUserException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }
}
