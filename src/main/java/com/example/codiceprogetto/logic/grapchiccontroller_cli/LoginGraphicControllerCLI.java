package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import com.example.codiceprogetto.logic.appcontroller.LoginApplicativeController;
import com.example.codiceprogetto.logic.bean.LoginBean;
import com.example.codiceprogetto.logic.exception.InvalidFormatException;
import com.example.codiceprogetto.logic.utils.PrinterCLI;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginGraphicControllerCLI extends AbsGraphicControllerCLI {

    @Override
    public void start() {
        int choice = -1;

        while (choice == -1) {
            try {
                choice = showMenu();
                handleChoice(choice);
            } catch (IOException | InvalidFormatException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
                choice = -1;
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        PrinterCLI.printf("--- Bubble Shop ---");
        PrinterCLI.printf("1. Login");
        PrinterCLI.printf("2. SignUp");
        PrinterCLI.printf("3. Quit");

        return getMenuChoice(1, 3);
    }

    private void handleChoice(int choice) throws InvalidFormatException {
        switch(choice) {
            case 1 -> login();
            case 2 -> new SignUpGraphicControllerCLI().start();
            case 3 -> System.exit(0);
            default -> throw new InvalidFormatException("Invalid choice");
        }
    }

    public void login() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        LoginApplicativeController login = new LoginApplicativeController();
        int ret;

        try {
            PrinterCLI.print("Email: ");
            String email = reader.readLine();
            PrinterCLI.print("Password: ");
            String password = reader.readLine();
            LoginBean loginBean = new LoginBean(email, password);

            ret = login.loginUser(loginBean);

            if (ret == -1) {
                Logger.getAnonymousLogger().log(Level.INFO, "Login error");
            } else {
                switch(SessionUser.getInstance().getThisUser().getUserType()) {
                    case CUSTOMER:
                        new HomeGraphicControllerCLI().start();
                        break;
                    case SELLER:
                        new IncomingOrderGraphicControllerCLI().start();
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }
}

