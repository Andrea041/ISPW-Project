package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import com.example.codiceprogetto.logic.appcontroller.CheckoutApplicativeController;
import com.example.codiceprogetto.logic.bean.AddressBean;
import com.example.codiceprogetto.logic.bean.CouponBean;
import com.example.codiceprogetto.logic.bean.ShippingBean;
import com.example.codiceprogetto.logic.exception.AlreadyAppliedCouponException;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.InvalidFormatException;
import com.example.codiceprogetto.logic.utils.PrinterCLI;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckoutGraphicControllerCLI extends AbsGraphicControllerCLI {
    SessionUser su = SessionUser.getInstance();
    CheckoutApplicativeController checkApp = new CheckoutApplicativeController();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void start() {
        int choice = -1;
        Scanner input = new Scanner(System.in);
        AddressBean addressBean = null;

        while(choice == -1) {
            try {
                choice = showMenu();
                switch (choice) {
                    case 1 -> {
                        PrinterCLI.print("Do you want to you use your memorized address or use a new one (digit OWN or NEW): ");
                        String choose = reader.readLine();

                        if (choose.equals("NEW")) {
                            PrinterCLI.print("Name: ");
                            String name = reader.readLine();
                            PrinterCLI.print("Last name: ");
                            String lastName = reader.readLine();
                            PrinterCLI.print("Address: ");
                            String address = reader.readLine();
                            PrinterCLI.print("State: ");
                            String state = reader.readLine();
                            PrinterCLI.print("City: ");
                            String city = reader.readLine();
                            PrinterCLI.print("Phone number: ");
                            String phone = reader.readLine();

                            addressBean = new AddressBean(state, city, phone, name, lastName, address);

                            if (checkApp.checkCustomerAddress(su.getThisUser().getEmail()) && askSave())
                                checkApp.insertAddress(addressBean, su.getThisUser().getEmail());
                        } else if (!choose.equals("OWN")) throw new InvalidFormatException("Invalid choice");

                        checkApp.createOrder(addressBean, su.getThisUser().getEmail());

                        new PaymentGraphicControllerCLI().start();
                    }
                    case 2 -> {
                        PrinterCLI.printf("1. FREE - 6 to 8 working days");
                        PrinterCLI.printf("2. 3.00€ - 4 to 6 working days");
                        PrinterCLI.printf("3. 5.00€ - 2 to 4 working days");
                        int ship = input.nextInt();
                        ShippingBean shippingBean = new ShippingBean();
                        
                        if (ship == 1)
                            shippingBean.setShippingValue(0);
                        else if (ship == 2) 
                            shippingBean.setShippingValue(3);
                        else if (ship == 3)
                            shippingBean.setShippingValue(5);
                        else throw new InvalidFormatException("Invalid choice");

                        checkApp.addShipping(shippingBean);
                    }
                    case 3 -> {
                        PrinterCLI.print("Insert your coupon: ");
                        String coupon = reader.readLine();

                        CouponBean couponBean = new CouponBean(coupon);
                        checkApp.checkCouponCode(couponBean);
                    }
                    case 4 -> checkApp.removeCoupon();
                    case 5 -> new SignUpGraphicControllerCLI().start();
                    default -> throw new InvalidFormatException("Invalid choice");
                }
            } catch (InvalidFormatException | DAOException | SQLException | AlreadyAppliedCouponException | IOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    @Override
    public int showMenu() {
        PrinterCLI.printf("--- Bubble Shop ---");
        PrinterCLI.printf("1. Insert address and go to payment");
        PrinterCLI.printf("2. Choose Shipping (DEFAULT: free shipping)");
        PrinterCLI.printf("3. Insert Coupon (DEFAULT: none)");
        PrinterCLI.printf("4. Delete active coupon");
        PrinterCLI.printf("5. Back");

        return getMenuChoice(1, 4);
    }

    private boolean askSave() throws IOException {
        PrinterCLI.print("Do you want to save your delivery address? (y/n)");
        String choose = reader.readLine();

        return choose.equals("y");
    }
}
