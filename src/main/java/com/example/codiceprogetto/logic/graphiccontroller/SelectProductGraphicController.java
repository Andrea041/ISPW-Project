package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.AddProductToCartApplicativeController;
import com.example.codiceprogetto.logic.bean.ProductBean;
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
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.sql.SQLException;

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
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private Label productName;
    @FXML
    private Label productID;
    private final String ACTION = "textUpdate";
    Timeline pause = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {alert.setText("");}));

    @FXML
    void initialize(){
        String size = "One Size";
        myChoiceBox.getItems().addAll(size);
        myChoiceBox.setValue("One Size");
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

        ProductBean prod = new ProductBean(productName.getText(), productID.getText(), Integer.parseInt(displayUnits.getText()), myChoiceBox.getValue());
        AddProductToCartApplicativeController addCobra = new AddProductToCartApplicativeController();

        try {
            ret = addCobra.updateCart(prod);
            if(ret == -1)
                alert(errorToDisplay);
        } catch (SQLException e) {
            alert(errorToDisplay);
        } catch (TooManyUnitsExcpetion | DAOException e) {
            alert(e.getMessage());
        }

        boolean choice = displayConfirmBox("Do you want stay on this page or go to shopping cart?", "Stay on this page", "Go to shopping cart");
        if(!choice)
            navigateTo(CART);
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

    public void scrollRight() {
        image2.setVisible(true);
        image1.setVisible(false);
    }

    public void scrollLeft() {
        image1.setVisible(true);
        image2.setVisible(false);
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
