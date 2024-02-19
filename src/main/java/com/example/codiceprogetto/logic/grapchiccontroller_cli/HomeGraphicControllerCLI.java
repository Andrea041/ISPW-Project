package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import com.example.codiceprogetto.logic.appcontroller.HomePageApplicativeController;
import com.example.codiceprogetto.logic.bean.ApprovedOrderBean;
import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;
import com.example.codiceprogetto.logic.exception.InvalidFormatException;
import com.example.codiceprogetto.logic.exception.NotLoggedUserException;
import com.example.codiceprogetto.logic.utils.PrinterCLI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeGraphicControllerCLI extends AbsGraphicControllerCLI {
    HomePageApplicativeController homeApp = new HomePageApplicativeController();

    @Override
    public void start() {
        int choice = -1;

        if (homeApp.checkLogin()) {
            ApprovedOrderBean approvedOrderBean;
            int approvedOrder = 0;
            int notApprovedOrder = 0;

            try {
                approvedOrderBean = homeApp.fetchAllOrders(new OrderBean(OrderStatus.CLOSED));
                approvedOrder = approvedOrderBean.getApprovedOrder();

                approvedOrderBean = homeApp.fetchAllOrders(new OrderBean(OrderStatus.CANCELLED));
                notApprovedOrder = approvedOrderBean.getNotApprovedOrder();
            } catch (SQLException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }

            if (approvedOrder != 0 && notApprovedOrder != 0)
                PrinterCLI.printf(String.format("You have %d approved order(s) and %d rejected order(s)!", approvedOrder, notApprovedOrder));
            else if (approvedOrder != 0)
                PrinterCLI.printf(String.format("You have %d approved order(s)!", approvedOrder));
            else if (notApprovedOrder != 0)
                PrinterCLI.printf(String.format("You have %d rejected order(s)!", notApprovedOrder));
        }

        while (choice == -1) {
            try {
                choice = showMenu();

                handleChoice(choice);

                if (choice == 2)
                    choice = -1;
            } catch (InvalidFormatException | IOException | NotLoggedUserException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
                choice = -1;
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        PrinterCLI.printf("--- Bubble Shop Home ---");
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