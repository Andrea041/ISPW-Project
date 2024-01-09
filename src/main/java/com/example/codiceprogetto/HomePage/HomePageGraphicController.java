package com.example.codiceprogetto;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageGraphicController extends Application {
    private Parent root;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/HomePage/HomePage.fxml"));
        stage.setTitle("Bubble Shop");
        stage.setScene(new Scene(root));
        stage.show();
        contentPane.setCenter(new HomePageGraphicController().getHomeView());
    }

    public Parent getHomeView(){
        return this.root;
    }

    public static void main(String[] args) {
        launch();
    }

    @FXML
    BorderPane contentPane;

    @FXML
    private Label manSwimWear;
    public void manSwimGUI(MouseEvent mouseEvent) {
        System.out.println("try");
    }
    public void manZoomEnter(MouseEvent mouseEvent) {
        System.out.println("try");
    }
    public void manZoomExit(MouseEvent mouseEvent) {
        System.out.println("try");
    }

    @FXML
    private Label womanSwimWear;
    public void womSwimGUI(MouseEvent mouseEvent) {
        System.out.println("try");
    }
    public void womZoomEnter(MouseEvent mouseEvent) {
        System.out.println("try");
    }
    public void womZoomExit(MouseEvent mouseEvent) {
        System.out.println("try");
    }

    @FXML
    private Label accessories;
    public void accessorGUI(MouseEvent mouseEvent) {
        contentPane.setCenter(new BrowseAccessoriesGraphicController().getView());
    }
    public void accZoomEnter(MouseEvent mouseEvent) {
        System.out.println("try");
    }
    public void accZoomExit(MouseEvent mouseEvent) {
        System.out.println("try");
    }

    @FXML
    private Label magazineText;
    public void magZoomEnter(MouseEvent mouseEvent) {
        System.out.println("try");
    }
    public void magZoomExit(MouseEvent mouseEvent) {
        System.out.println("try");
    }

    @FXML
    private Label myAccount;
    public void accountGUI(MouseEvent mouseEvent){
        System.out.println("try");
    }

    @FXML
    private Rectangle CartRec;
    @FXML
    private Label Cart;
    @FXML
    private Label itemInCart;
    public void cartGUI(MouseEvent mouseEvent) {
        System.out.println("try");
    }
}