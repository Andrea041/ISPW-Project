package com.example.codiceprogetto.BrowseProduct;

import com.example.codiceprogetto.HomePage.HomePageView;
import com.example.codiceprogetto.SelectCobra.SelectCobraView;
import com.example.codiceprogetto.ShoppingCart.ShoppingCartView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BrowseAccessoriesGraphicController {
    private Stage stage;
    private Scene scene;
    public void backHomePage(MouseEvent mouseEvent) throws Exception{
        stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(new HomePageView().getHomeView());
        stage.setScene(scene);
        stage.show();
    }

    public void accountGUI(MouseEvent mouseEvent){
        System.out.println("try");
    }

    public void cartGUI(MouseEvent mouseEvent) throws Exception{
        stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(new ShoppingCartView().getShoppingView());
        stage.setScene(scene);
        stage.show();
    }

    public void selectProduct(MouseEvent mouseEvent) throws Exception{
        stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(new SelectCobraView().getCobraView());
        stage.setScene(scene);
        stage.show();
    }
}
