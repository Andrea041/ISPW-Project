package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import java.util.Scanner;

import com.example.codiceprogetto.logic.exception.NotLoggedUserException;
import com.example.codiceprogetto.logic.utils.PrinterCLI;
import com.example.codiceprogetto.logic.utils.SessionUser;

public abstract class AbsGraphicControllerCLI implements GraphicControllerInterfaceCLI {
    protected int getMenuChoice(int min, int max) {
        Scanner input = new Scanner(System.in);
        int choice;

        while (true) {
            PrinterCLI.print("Please, make a choice: ");

            choice = input.nextInt();
            if (choice >= min && choice <= max) break;

            PrinterCLI.printf("Not valid choice!");
        }

        return choice;
    }

    public void logoutUser() throws NotLoggedUserException {
        SessionUser sessionUser = SessionUser.getInstance();

        if(sessionUser.getAllUser().contains(sessionUser.getThisUser()))
            sessionUser.logout();
        else
            throw new NotLoggedUserException("You are not logged in!");
    }

    public boolean checkLogin() {
        SessionUser sessionUser = SessionUser.getInstance();

        return sessionUser.getAllUser().contains(sessionUser.getThisUser());
    }
}
