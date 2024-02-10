package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.ProdInCartApplicativeController;
import com.example.codiceprogetto.logic.bean.ProductInCartBean;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.GraphicTool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdInCartGraphicController extends GraphicTool {
    @FXML
    private Label totalAmountPerProd;
    @FXML
    private Label productName;
    @FXML
    private TextField changeQuantity;
    private int counter = 0;

    @FXML
    void initialize() {
        //updateTotalSingleProd();
    }

    public void removeUnits(ActionEvent mouseEvent) {
        Stage rootToDisplay = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        int res;

        counter--;
        if(counter < 1)
            counter = 1;
        changeQuantity.setText(Integer.toString(counter));

        ProdInCartApplicativeController prodBox = new ProdInCartApplicativeController();
        try {
            res = prodBox.changeUnits(productName.getText(), "DELETE");
            if(res <= 0)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");
        } catch(TooManyUnitsExcpetion e) {
            alert(e.getMessage(), rootToDisplay);
        } catch(SQLException | DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        updateTotalSingleProd();
    }

    public void addUnits(ActionEvent mouseEvent) {
        Stage rootToDisplay = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        int res;

        counter++;
        if(counter > 10)
            counter = 10;
        changeQuantity.setText(Integer.toString(counter));

        ProdInCartApplicativeController prodBox = new ProdInCartApplicativeController();
        try {
            res = prodBox.changeUnits(productName.getText(), "ADD");
            if(res <= 0)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");
        } catch(TooManyUnitsExcpetion e) {
            alert(e.getMessage(), rootToDisplay);
        } catch(SQLException | DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        updateTotalSingleProd();
    }

    public void removeProd() {
        ProdInCartApplicativeController prodBox = new ProdInCartApplicativeController();
        int res;

        try {
            res = prodBox.removeProduct(productName.getText());
            if(res == -1)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");
        } catch(DAOException | TooManyUnitsExcpetion e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }
    }

    public void updateTotalSingleProd() {
        int selectedUnits;

        ProdInCartApplicativeController prodBox = new ProdInCartApplicativeController();
        ProductInCartBean cartTotal = new ProductInCartBean(0);

        try {
            cartTotal = prodBox.calculateTotalSingleProd(productName.getText(), cartTotal);
            if(cartTotal == null)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");

            selectedUnits = prodBox.displaySelectedUnits(productName.getText());
            if(selectedUnits == -1)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");

            totalAmountPerProd.setText(round(cartTotal.getTotalAmount(), 2) + "€");
            changeQuantity.setText(String.valueOf(selectedUnits));
            counter = selectedUnits;

            cartTotal.notifySubscriber();
        } catch(DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }
    }
}
