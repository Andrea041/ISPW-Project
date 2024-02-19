package com.example.codiceprogetto.logic.grapchiccontroller_cli;

import com.example.codiceprogetto.logic.appcontroller.AddProductToCartApplicativeController;
import com.example.codiceprogetto.logic.bean.ProductBean;
import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.InvalidFormatException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.PrinterCLI;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectProductGraphicControllerCLI extends AbsGraphicControllerCLI {
    private final ProductStockBean productStockBean;
    AddProductToCartApplicativeController prodAdd = new AddProductToCartApplicativeController();

    protected SelectProductGraphicControllerCLI(ProductStockBean productStockBean) {
        this.productStockBean = productStockBean;
    }

    @Override
    public void start() {
        ProductBean productBean;
        int choice = -1;

        while(choice == -1) {
            try {
                choice = showMenu();
                if (choice == 0)
                    new BrowseAccessoriesGraphicControllerCLI().start();
                else if (choice >= 1 && choice <= 10) {
                    productBean = new ProductBean(productStockBean.getProductName(), productStockBean.getLabelID(), choice);

                    int ret = prodAdd.updateCart(productBean, SessionUser.getInstance().getThisUser().getEmail());
                    if (ret == -1)
                        Logger.getAnonymousLogger().log(Level.INFO, "Updating cart error");

                    new ShoppingCartGraphicControllerCLI().start();
                } else
                    throw new InvalidFormatException("Invalid choice");
            } catch (IOException | InvalidFormatException | DAOException |SQLException | TooManyUnitsExcpetion e){
                choice = -1;
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        Scanner input = new Scanner(System.in);

        PrinterCLI.printf("--- Bubble Shop Accessories ---");
        PrinterCLI.print("How many units to buy? (min: 1 and max: 10 units per customer) or digit 0 to go back: ");

        return input.nextInt();
    }
}
