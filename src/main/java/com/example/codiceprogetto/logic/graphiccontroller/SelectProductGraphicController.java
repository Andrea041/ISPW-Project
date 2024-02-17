package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.AddProductToCartApplicativeController;
import com.example.codiceprogetto.logic.bean.ProductBean;
import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.GraphicTool;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectProductGraphicController extends GraphicTool {
    private int unitsCounter = 1;
    @FXML
    private TextField displayUnits;
    @FXML
    private ChoiceBox<String> myChoiceBox;
    @FXML
    private Label selectionSize;
    @FXML
    private Label alert;
    @FXML
    private ImageView image;
    @FXML
    private Label productName;
    @FXML
    private Label productID;
    @FXML
    private Label prodPrice;
    protected static final String ACTION = "textUpdate";
    private final ProductStockBean prod;
    Timeline pause = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {alert.setText("");}));

    protected SelectProductGraphicController(ProductStockBean prod) {
        this.prod = prod;
    }

    @FXML
    void initialize(){
        String size = "One Size";
        myChoiceBox.getItems().addAll(size);
        myChoiceBox.setValue("One Size");

        productName.setText(prod.getProductName());
        prodPrice.setText(round(prod.getPrice(), 2) + "€");
        productID.setText(prod.getLabelID());

        try {
            Image prodImage = new Image(new FileInputStream(prod.getProdImage()));
            image.setImage(prodImage);
        } catch(FileNotFoundException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }

    public void setChoiceBox() {
        String selected = myChoiceBox.getValue();
        selectionSize.setText(selected);
    }

    public void accountGUI() {
        SessionUser.getInstance().logout();
        navigateTo(LOGIN);
    }

    public void cartGUI() {
        navigateTo(CART);
    }

    public void addProduct() {
        int ret;
        String errorToDisplay = "Unknown error";

        ProductBean productBean = new ProductBean(productName.getText(), productID.getText(), Integer.parseInt(displayUnits.getText()), myChoiceBox.getValue());
        AddProductToCartApplicativeController addCobra = new AddProductToCartApplicativeController();

        try {
            ret = addCobra.updateCart(productBean);
            if(ret == -1)
                alert(errorToDisplay);

            boolean choice = displayConfirmBox("Do you want stay on this page or go to shopping cart?", "Stay on this page", "Go to shopping cart");
            if(!choice)
                navigateTo(CART);
        } catch (SQLException e) {
            alert(errorToDisplay);
        } catch (TooManyUnitsExcpetion | DAOException e) {
            alert(e.getMessage());
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }
    }

    public void addProductUnits() {
        unitsCounter++;
        if(unitsCounter > 10) {
            unitsCounter = 10;
            textFieldHandler("alert");
        }
        else
            textFieldHandler(ACTION);
    }

    public void removeProductUnits() {
        unitsCounter--;
        if(unitsCounter < 1)
            unitsCounter = 1;
        else
            textFieldHandler(ACTION);
    }

    public void backBrowseProduct() {
        navigateTo(ACC);
    }

    public void textFieldHandler(String toDo){
        if(toDo.equals(ACTION))
            displayUnits.setText(Integer.toString(unitsCounter));
        else if(toDo.equals("alert")){
            alert.setText("Max 10 units per customer");
            pause.play();
        }
    }
}
