package com.example.codiceprogetto.browseProduct;

import com.example.codiceprogetto.homepage.HomePageView;
import com.example.codiceprogetto.selectcobra.SelectCobraView;
import com.example.codiceprogetto.shoppingcart.ShoppingCartView;
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
