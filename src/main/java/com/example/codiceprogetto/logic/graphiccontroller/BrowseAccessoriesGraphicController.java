package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.BrowseAccessoriesApplicativeController;
import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.exception.NotLoggedUserException;
import com.example.codiceprogetto.logic.utils.Utilities;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowseAccessoriesGraphicController extends Utilities {
    @FXML
    private GridPane prodGrid;
    BrowseAccessoriesApplicativeController broAcc;

    @FXML
    void initialize() {
        List<ProductStockBean> productList;
        broAcc = new BrowseAccessoriesApplicativeController();
        int gridRow = 0;
        int gridCol = 0;
        int index = 1;

        try {
            productList = broAcc.retrieveProduct();

            for(ProductStockBean prod : productList) {
                ProductPreviewGraphicController prodPrev = new ProductPreviewGraphicController(prod);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/codiceprogetto/FXML/BrowseAccessories/ProductPreview.fxml"));
                fxmlLoader.setController(prodPrev);

                Parent root = fxmlLoader.load();

                prodGrid.add(root, gridCol, gridRow);

                if(index % 2 == 0) {
                    gridRow++;
                    gridCol = 0;
                }
                 else
                    gridCol++;
                index++;
            }
        } catch(IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }

    public void backHomePage() {
        navigateTo(HOME);
    }

    public void accountGUI(){
        try {
            broAcc.logoutUser();
            navigateTo(HOME);
        } catch (NotLoggedUserException e) {
            alert("You are not logged in!");
        }
    }

    public void cartGUI() {
        if(broAcc.checkLogin())
            navigateTo(CART);
        else {
            alert("You have to login to see your cart!");
            navigateTo(LOGIN);
        }
    }

    public void login() {
        navigateTo(LOGIN);
    }
}
