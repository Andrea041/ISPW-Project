package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import com.example.codiceprogetto.logic.appcontroller.PaymentApplicativeController;
import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.bean.TransactionBean;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.InvalidFormatException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.PrinterCLI;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentSummaryGraphicControllerCLI extends AbsGraphicControllerCLI {
    OrderBean orderBean = new OrderBean();
    TransactionBean transactionBean = new TransactionBean();
    @Override
    public void start() {
        printPaymentSummary();
        int choice;

        while (true) {
            try {
                choice = showMenu();
                switch (choice) {
                    case 0 -> new HomeGraphicControllerCLI().start();
                    case 1 -> System.exit(0);
                    default -> throw new InvalidFormatException("Invalid choice");
                }
            } catch (InvalidFormatException | IOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        PrinterCLI.printf("0. Back Home");
        PrinterCLI.printf("1. Quit");

        return getMenuChoice(0, 1);
    }

    private void printPaymentSummary() {
        PaymentApplicativeController toPay = new PaymentApplicativeController();
        String summaryString;

        try {
            orderBean = toPay.fetchTotal(SessionUser.getInstance().getThisUser().getEmail(), orderBean);
            transactionBean = toPay.fetchTransaction(orderBean);

            toPay.makeCartEmpty(SessionUser.getInstance().getThisUser().getEmail());
            orderBean.setOrderStatus(OrderStatus.CONFIRMED);
        } catch (SQLException | DAOException | TooManyUnitsExcpetion e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        PrinterCLI.printf("--- Bubble Shop Payment Summary ---");

        summaryString = String.format("Payed amount: %f€", orderBean.getFinalTotal());
        PrinterCLI.printf(summaryString);
        summaryString = String.format("Payment method: %s", transactionBean.getPaymentType());
        PrinterCLI.printf(summaryString);
        summaryString = String.format("Order id: %s", orderBean.getOrderID());
        PrinterCLI.printf(summaryString);
        summaryString = String.format("Order status: %s", orderBean.getOrderStatus());
        PrinterCLI.printf(summaryString);
        summaryString = String.format("Customer email: %s", SessionUser.getInstance().getThisUser().getEmail());
        PrinterCLI.printf(summaryString);
    }
}
