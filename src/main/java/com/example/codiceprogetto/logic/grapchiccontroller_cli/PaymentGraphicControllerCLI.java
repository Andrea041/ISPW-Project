package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import com.example.codiceprogetto.logic.appcontroller.PaymentApplicativeController;
import com.example.codiceprogetto.logic.bean.PaymentBean;
import com.example.codiceprogetto.logic.enumeration.PaymentType;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.InvalidFormatException;
import com.example.codiceprogetto.logic.utils.PrinterCLI;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentGraphicControllerCLI extends AbsGraphicControllerCLI {
    PaymentApplicativeController toPay = new PaymentApplicativeController();
    SessionUser su = SessionUser.getInstance();
    int count = 1;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void start() {
        int choice = -1;
        String choose;
        PaymentBean paymentBean;

        while(choice == -1) {
            try {
                choice = showMenu();
                switch (choice) {
                    case 1 -> {
                        if (count % 2 == 0) {
                            Logger.getAnonymousLogger().log(Level.INFO, "Payment rejected, order deleted!");
                            new HomeGraphicControllerCLI().start();
                        }

                        PrinterCLI.print("Do you want to pay with your credit card or with paypal? (digit CARD or PAYPAL)");
                        choose = reader.readLine();
                        PaymentType paymentType = PaymentType.PAYPAL;

                        if(choose.equals("CARD")) {
                            paymentType = PaymentType.CARD;

                            PrinterCLI.print("Do you want to you use your memorized payment method or use a new one (digit OWN or NEW): ");
                            choose = reader.readLine();

                            if(choose.equals("NEW")) {
                                PrinterCLI.print("Name: ");
                                String name = reader.readLine();
                                PrinterCLI.print("Last name: ");
                                String lastName = reader.readLine();
                                PrinterCLI.print("Card number: ");
                                String cardNumber = reader.readLine();
                                PrinterCLI.print("Cvv: ");
                                String cvv = reader.readLine();
                                PrinterCLI.print("Expiration: ");
                                String expiration = reader.readLine();
                                PrinterCLI.print("Billing zip code: ");
                                String zip = reader.readLine();

                                paymentBean = new PaymentBean(name, lastName, expiration, cardNumber, cvv, zip);

                                if (toPay.checkCustomerPayment(su.getThisUser().getEmail()) && askSave())
                                    toPay.insertPayment(paymentBean, su.getThisUser().getEmail());
                            } else if (!choose.equals("OWN")) throw new InvalidFormatException("Invalid choice");

                            toPay.createTransaction(su.getThisUser().getEmail(), paymentType.getId());

                            new PaymentSummaryGraphicControllerCLI().start();
                        }
                    }
                    case 2 -> {
                        toPay.deleteOrder(su.getThisUser().getEmail());
                        new HomeGraphicControllerCLI().start();
                    }
                    default -> throw new InvalidFormatException("Invalid choice");
                }
            } catch (IOException | DAOException | InvalidFormatException | SQLException e) {
                choice = -1;
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        PrinterCLI.printf("--- Bubble Shop ---");
        PrinterCLI.printf("1. Choose payment method and complete your payment");
        PrinterCLI.printf("2. Back (back to home!)");

        return getMenuChoice(1, 2);
    }

    private boolean askSave() throws IOException {
        PrinterCLI.print("Do you want to save your payment method? (y/n)");
        String choose = reader.readLine();

        return choose.equals("y");
    }
}
