package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import com.example.codiceprogetto.logic.appcontroller.SignupApplicativeController;
import com.example.codiceprogetto.logic.bean.SignupBean;
import com.example.codiceprogetto.logic.enumeration.UserType;
import com.example.codiceprogetto.logic.exception.ControlSquenceException;
import com.example.codiceprogetto.logic.exception.InvalidFormatException;
import com.example.codiceprogetto.logic.utils.PrinterCLI;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpGraphicControllerCLI extends AbsGraphicControllerCLI {
    private static final String KEY = "ISPW2324";
    @Override
    public void start() {
        int choice = -1;
        while (choice == -1) {
            try {
                choice = showMenu();
                switch(choice) {
                    case 1 -> signup();
                    case 2 -> new LoginGraphicControllerCLI().start();
                    case 3 -> System.exit(0);
                    default -> throw new InvalidFormatException("Invalid choice");
                }
            } catch (IOException | InvalidFormatException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
                choice = -1;
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        PrinterCLI.printf("--- Bubble Shop ---");
        PrinterCLI.printf("1. SignUp");
        PrinterCLI.printf("2. Login");
        PrinterCLI.printf("3. Quit");

        return getMenuChoice(1, 3);
    }

    public void signup() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SignupBean signBean;
        SignupApplicativeController signup = new SignupApplicativeController();

        try {
            PrinterCLI.print("Name: ");
            String name = reader.readLine();
            PrinterCLI.print("Surname: ");
            String surname = reader.readLine();
            PrinterCLI.print("Email: ");
            String email = reader.readLine();
            PrinterCLI.print("Password: ");
            String password = reader.readLine();
            PrinterCLI.print("Confirm Password: ");
            String confirmPassword = reader.readLine();
            PrinterCLI.print("Register as seller? (y/n): ");
            String sellerChoice = reader.readLine();

            if (!confirmPassword.equals(password))
                throw new ControlSquenceException("Inserted passwords don't match!");

            signBean = new SignupBean(email, password, name, surname);
            signBean.setUserType(UserType.CUSTOMER.getId());

            String sellerKey;
            if(sellerChoice.equals("y")) {
                PrinterCLI.print("Insert key: ");
                sellerKey = reader.readLine();

                if(!sellerKey.equals(KEY))
                    throw new ControlSquenceException("Sorry wrong key!");

                signBean.setUserType(UserType.SELLER.getId());
            } else
                throw new InvalidFormatException("Invalid choice: digit 'y' or 'n'");

            int ret = signup.signupUser(signBean);

            if (ret != 1) {
                switch (SessionUser.getInstance().getThisUser().getUserType()) {
                    case "CUSTOMER":
                        new HomeGraphicControllerCLI().start();
                        break;
                    case "SELLER":
                        new IncomingOrderGraphicControllerCLI();
                        break;
                    default:
                        break;
                }
            } else {
                Logger.getAnonymousLogger().log(Level.INFO,"Signup error");
            }
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }
}
