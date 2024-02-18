package com.example.codiceprogetto;

import com.example.codiceprogetto.logic.utils.NavigatorSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AppStarter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/codiceprogetto/FXML/HomePage/HomePage.fxml")));

        NavigatorSingleton nav = NavigatorSingleton.getInstance(stage);

        nav.getStage().setTitle("Bubble Shop");
        nav.getStage().setResizable(false);
        nav.getStage().setScene(new Scene(root));
        nav.getStage().show();
    }

    public static void main(String[] args){
        launch();
    }
}
