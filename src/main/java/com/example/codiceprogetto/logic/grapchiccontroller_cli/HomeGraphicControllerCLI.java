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
        int choice = -1;

        while (choice == -1) {
            try {
                choice = showMenu();

                if (choice == 2)
                    choice = -1;

                handleChoice(choice);
            } catch (InvalidFormatException | IOException | NotLoggedUserException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
                choice = -1;
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        PrinterCLI.printf("--- Bubble Shop ---");
        PrinterCLI.printf("1. Login");
        PrinterCLI.printf("2. Logout");
        PrinterCLI.printf("3. See our accessories");
        PrinterCLI.printf("4. Go to shopping cart");
        PrinterCLI.printf("5. Quit");

        return getMenuChoice(1, 5);
    }

    private void handleChoice(int choice) throws InvalidFormatException, NotLoggedUserException {
        switch(choice) {
            case 1 -> new LoginGraphicControllerCLI().start();
            case 2 -> homeApp.logoutUser();
            case 3 -> new BrowseAccessoriesGraphicControllerCLI().start();
            case 4 -> new ShoppingCartGraphicControllerCLI().start();
            case 5 -> System.exit(0);
            default -> throw new InvalidFormatException("Invalid choice");
        }
    }
}