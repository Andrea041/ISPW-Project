package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.AddProductToCartApplicativeController;
import com.example.codiceprogetto.logic.bean.ProductBean;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.GraphicTool;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;

public class SelectCobraGraphicController extends GraphicTool {
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
    private ImageView occhialini1;
    @FXML
    private ImageView occhialini2;
    private static final String ACTION = "textUpdate";
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

    public void accountGUI(MouseEvent mouseEvent) {
        System.out.println("try");
    }

    public void cartGUI(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "CART");
    }

    public void addProduct(MouseEvent mouseEvent) {
        int ret;
        Stage rootToDisplay = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        String errorToDisplay = "Unknown error";

        ProductBean prod = new ProductBean("Cobra ultra swipe mirror", 6452, Integer.parseInt(displayUnits.getText()), myChoiceBox.getValue());
        AddProductToCartApplicativeController addCobra = new AddProductToCartApplicativeController();

        try {
            ret = addCobra.updateCart(prod);
            if(ret == -1)
                alert(errorToDisplay, rootToDisplay);
        } catch (SQLException e) {
            alert(errorToDisplay, rootToDisplay);
        } catch (TooManyUnitsExcpetion e) {
            alert(e.getMessage(), rootToDisplay);
        }

        boolean choice = displayConfirmBox("Do you want stay on this page or go to shopping cart?", "Stay on this page", "Go to shopping cart");
        if(!choice)
            navigateTo(mouseEvent, "CART");
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

    public void backBrowseProduct(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "ACC");
    }

    public void scrollRight() {
        occhialini2.setVisible(true);
        occhialini1.setVisible(false);
    }

    public void scrollLeft() {
        occhialini1.setVisible(true);
        occhialini2.setVisible(false);
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
