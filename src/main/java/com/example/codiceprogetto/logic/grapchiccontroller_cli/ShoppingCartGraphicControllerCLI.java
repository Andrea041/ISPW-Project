package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import com.example.codiceprogetto.logic.appcontroller.ProdInCartApplicativeController;
import com.example.codiceprogetto.logic.appcontroller.ShoppingCartApplicativeController;
import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.InvalidFormatException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.PrinterCLI;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShoppingCartGraphicControllerCLI extends AbsGraphicControllerCLI {
    List<ProductStockBean> productList;
    ShoppingCartApplicativeController shopApp = new ShoppingCartApplicativeController();
    ProdInCartApplicativeController prodApp = new ProdInCartApplicativeController();

    @Override
    public void start() {
        int choice = -1;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (choice == -1) {
            try {
                if (shopApp.checkLogin()) {
                    productList = shopApp.retrieveCartProd();

                    PrinterCLI.printf("--- Your cart ---");
                    printCart();

                    if (!productList.isEmpty()) {
                        choice = showMenu();

                        switch (choice) {
                            case 1 -> new CheckoutGraphicControllerCLI().start();
                            case 2 -> {
                                addRemoveUnits(reader);
                                choice = -1;
                            }
                            case 3 -> {
                                removeProduct(reader);
                                choice = -1;
                            }
                            case 4 -> new HomeGraphicControllerCLI().start();
                            default -> throw new InvalidFormatException("Invalid choice");
                        }
                    } else {
                        PrinterCLI.printf("Your cart is empty!");
                        new HomeGraphicControllerCLI().start();
                    }
                } else {
                    PrinterCLI.printf("You have to login to see your cart! Redirecting to home");
                    new HomeGraphicControllerCLI().start();
                }
            } catch (InvalidFormatException | DAOException | SQLException | TooManyUnitsExcpetion | IOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
                choice = -1;
            }
        }
    }

    private void addRemoveUnits(BufferedReader reader) throws IOException, InvalidFormatException, DAOException, SQLException, TooManyUnitsExcpetion {
        ProductStockBean prod;
        PrinterCLI.print("Insert product ID: ");
        String prodID = reader.readLine();

        prod = findProductInCartById(prodID);
        if (prod == null)
            throw new InvalidFormatException("Choose a valid product ID");

        PrinterCLI.print("Do you want to add or remove units? (Digit 'ADD' or 'DELETE') ");
        String op = reader.readLine();

        prodApp.changeUnits(prodID, op, SessionUser.getInstance().getThisUser().getEmail());
    }

    private void removeProduct(BufferedReader reader) throws IOException, InvalidFormatException, DAOException, SQLException, TooManyUnitsExcpetion {
        ProductStockBean prod;
        PrinterCLI.print("Insert product ID: ");
        String prodID = reader.readLine();

        prod = findProductInCartById(prodID);
        if (prod != null)
            prodApp.removeProduct(prodID, SessionUser.getInstance().getThisUser().getEmail());
        else
            throw new InvalidFormatException("Choose a valid product ID");
    }


    @Override
    public int showMenu() throws IOException {
        PrinterCLI.printf("--- Bubble Shop Cart ---");
        PrinterCLI.printf("1. Go to checkout");
        PrinterCLI.printf("2. Change product units");
        PrinterCLI.printf("3. Remove product");
        PrinterCLI.printf("4. Back");

        return getMenuChoice(1, 4);
    }

    private void printCart() {
        String productString;

        for(ProductStockBean productStockBean : productList) {
            productString = String.format("Product ID: %s, name: %s, productPrice: %f, quantity: %d", productStockBean.getLabelID(),
                    productStockBean.getProductName(),
                    productStockBean.getPrice(),
                    productStockBean.getSelectedUnits());

            PrinterCLI.printf(productString);
        }
    }

    private ProductStockBean findProductInCartById(String id) {
        for(ProductStockBean p : productList)
            if(p.getLabelID().equals(id)) return p;

        return null;
    }
}
