package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import com.example.codiceprogetto.logic.appcontroller.IncomingOrderApplicativeController;
import com.example.codiceprogetto.logic.appcontroller.OrderSellerApplicativeController;
import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.InvalidFormatException;
import com.example.codiceprogetto.logic.exception.NotLoggedUserException;
import com.example.codiceprogetto.logic.utils.PrinterCLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IncomingOrderCLI extends AbsGraphicControllerCLI {
    IncomingOrderApplicativeController incOrder = new IncomingOrderApplicativeController();
    OrderSellerApplicativeController ordApp = new OrderSellerApplicativeController();
    List<OrderBean> orderBeanList;

    @Override
    public void start() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        OrderBean order;

        while (true) {
            int choice;
            try {
                orderBeanList = incOrder.retrieveOrders();
                printOrder();

                choice = showMenu();
                switch(choice) {
                    case 1 -> {
                        PrinterCLI.print("Insert order ID: ");
                        String orderID = reader.readLine();

                        order = findOrderInList(orderID);
                        if(order != null)
                            ordApp.acceptOrder(orderID);
                        else throw new InvalidFormatException("Choose a valid order ID");
                    }
                    case 2 -> {
                        logout();
                        new HomeGraphicControllerCLI().start();
                    }
                    case 3 -> System.exit(0);
                    default -> throw new InvalidFormatException("Invalid choice");
                }
            } catch (InvalidFormatException | IOException | SQLException | DAOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        PrinterCLI.printf("1. Approve an order");
        PrinterCLI.printf("2. Logout");
        PrinterCLI.printf("3. Quit");

        return getMenuChoice(1, 3);
    }

    private void printOrder() {
        PrinterCLI.printf("--- Bubble Shop Incoming Order ---");
        String orderString;

        for (OrderBean orderBean : orderBeanList) {
            orderString = String.format("Order ID: %s, total amount: %f, customer username: %s, State of delivery: %s" ,
                    orderBean.getOrderID(),
                    orderBean.getFinalTotal(),
                    orderBean.getEmail(),
                    orderBean.getAddress());

            PrinterCLI.printf(orderString);
        }
    }

    private void logout() {
        try {
            incOrder.logoutUser();
            Logger.getAnonymousLogger().log(Level.INFO, "Logout performed");
        } catch (NotLoggedUserException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }

    private OrderBean findOrderInList(String id) {
        for(OrderBean order : orderBeanList)
            if(order.getOrderID().equals(id)) return order;

        return null;
    }
}
