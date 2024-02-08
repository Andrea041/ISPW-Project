package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.ProdInCartApplicativeController;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.GraphicTool;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
        double totalPerProd;
        int selectedUnits;

        ProdInCartApplicativeController prodBox = new ProdInCartApplicativeController();

        try {
            totalPerProd = prodBox.calculateTotalSingleProd(productName.getText());
            if(totalPerProd == -1)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");

            selectedUnits = prodBox.displaySelectedUnits(productName.getText());
            if(selectedUnits == -1)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");

            totalAmountPerProd.setText(totalPerProd + "€");
            changeQuantity.setText(String.valueOf(selectedUnits));
            counter = selectedUnits;
        } catch (DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }
    }

    public void removeUnits() {
        counter--;
        if(counter < 1)
            counter = 1;
        changeQuantity.setText(Integer.toString(counter));
    }

    public void addUnits() {
        counter++;
        if(counter > 10)
            counter = 10;
        changeQuantity.setText(Integer.toString(counter));
    }

    public void removeProd(MouseEvent mouseEvent) {
    }
}
