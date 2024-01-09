package com.example.codiceprogetto;

import com.example.codiceprogetto.BrowseProduct.BrowseAccessoriesView;
import com.example.codiceprogetto.HomePage.HomePageGraphicController;
import com.example.codiceprogetto.HomePage.HomePageView;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppStarter extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/LoggingForm/LoginForm.fxml"));
        stage.setTitle("Bubble Shop");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
