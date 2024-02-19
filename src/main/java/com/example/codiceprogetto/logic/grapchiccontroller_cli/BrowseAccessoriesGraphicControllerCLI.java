package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import com.example.codiceprogetto.logic.appcontroller.AddProductToCartApplicativeController;
import com.example.codiceprogetto.logic.appcontroller.BrowseAccessoriesApplicativeController;
import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.exception.InvalidFormatException;
import com.example.codiceprogetto.logic.utils.PrinterCLI;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowseAccessoriesGraphicControllerCLI extends AbsGraphicControllerCLI {
    BrowseAccessoriesApplicativeController broAcc = new BrowseAccessoriesApplicativeController();
    AddProductToCartApplicativeController prodAdd = new AddProductToCartApplicativeController();
    List<ProductStockBean> productList;

    @Override
    public void start() {
        int choice = -1;

        while(choice == -1) {
            try {
                productList = broAcc.retrieveProduct();
                choice = showMenu();

                if (choice == 0)
                    new HomeGraphicControllerCLI().start();
                else {
                    ProductStockBean prodBean = findProductByChoice(choice);

                    if (prodBean != null && prodAdd.checkLogin())
                        new SelectProductGraphicControllerCLI(prodBean).start();
                    else if (prodBean != null && !prodAdd.checkLogin()) {
                        PrinterCLI.printf("Sorry you have to login first. Redirecting to login form...");
                        new LoginGraphicControllerCLI().start();
                    } else
                        throw new InvalidFormatException("Choose a valid product ID");
                }
            } catch (IOException | InvalidFormatException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
                choice = -1;
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        Scanner input = new Scanner(System.in);

        PrinterCLI.print("--- Bubble Shop Accessories ---\n");
        for (ProductStockBean prod : productList) {
            String productString = String.format("productId: %s, name: %s, price: %f", prod.getLabelID(), prod.getProductName(), prod.getPrice());

            PrinterCLI.printf(productString);
        }

        PrinterCLI.print("Digit the ID of the product that you want to buy or digit 0 to go back: ");

        return input.nextInt();
    }

    private ProductStockBean findProductByChoice(int choice) {
        for (ProductStockBean prod : productList) {
            if (prod.getLabelID().equals(String.valueOf(choice))) {
                return prod;
            }
        }
        return null;
    }
}
