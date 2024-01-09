package com.example.codiceprogetto.selectcobra;

import com.example.codiceprogetto.browseProduct.BrowseAccessoriesView;
import com.example.codiceprogetto.shoppingcart.ShoppingCartView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SelectCobraGraphicController{
    private Stage stage;
    private Scene scene;
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
    Timeline pause = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {alert.setText("");}));

    @FXML
    void initialize(){
        String size = "One Size";
        myChoiceBox.getItems().addAll(size);
        myChoiceBox.setValue("One Size");
    }

    public void setChoiceBox(ActionEvent event) {
        String selected = myChoiceBox.getValue();
        selectionSize.setText(selected);
    }

    public void accountGUI(MouseEvent mouseEvent) {
        System.out.println("try");
    }

    public void cartGUI(MouseEvent mouseEvent) throws Exception{
        stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(new ShoppingCartView().getShoppingView());
        stage.setScene(scene);
        stage.show();
    }

    public void addProduct(MouseEvent mouseEvent) {
        System.out.println("try");
    }

    public void addProductUnits(MouseEvent mouseEvent) {
        unitsCounter++;
        if(unitsCounter > 10) {
            unitsCounter = 10;
            textFieldHandler("alert");
        }
        else
            textFieldHandler("textUpdate");
    }

    public void removeProductUnits(MouseEvent mouseEvent) {
        unitsCounter--;
        if(unitsCounter < 1)
            unitsCounter = 1;
        else
            textFieldHandler("textUpdate");
    }

    public void backBrowseProduct(MouseEvent mouseEvent) throws Exception{
        stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(new BrowseAccessoriesView().getAccessoriesView());
        stage.setScene(scene);
        stage.show();
    }

    public void scrollRight(MouseEvent mouseEvent) {
        occhialini2.setVisible(true);
        occhialini1.setVisible(false);
    }

    public void scrollLeft(MouseEvent mouseEvent) {
        occhialini1.setVisible(true);
        occhialini2.setVisible(false);
    }

    public void textFieldHandler(String toDo){
        if(toDo.equals("textUpdate"))
            displayUnits.setText(Integer.toString(unitsCounter));
        else if(toDo.equals("alert")){
            alert.setText("Max 10 units per customer");
            pause.play();
        }
    }
}
