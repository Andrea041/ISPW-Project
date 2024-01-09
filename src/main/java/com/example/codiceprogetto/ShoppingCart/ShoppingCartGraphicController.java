package com.example.codiceprogetto.ShoppingCart;

import com.example.codiceprogetto.HomePage.HomePageView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShoppingCartGraphicController {
    private Stage stage;
    private Scene scene;
    public void back(MouseEvent mouseEvent) throws Exception{
        stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(new HomePageView().getHomeView());
        stage.setScene(scene);
        stage.show();
    }

    public void accountGUI(MouseEvent mouseEvent) {
    }

    public void cartGUI(MouseEvent mouseEvent) throws Exception{
        stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(new ShoppingCartView().getShoppingView());
        stage.setScene(scene);
        stage.show();
    }

    public void gotoCheckoutGUI(MouseEvent mouseEvent) {
    }
}
