package com.example.codiceprogetto.logic.utils;

import com.example.codiceprogetto.AppStarter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NavigatorSingleton {
    private static NavigatorSingleton instance = null;

    protected Stage stage;

    public Stage getStage() {
        return this.stage;
    }

    protected NavigatorSingleton(Stage stage) {
        this.stage = stage;
    }

    public static synchronized NavigatorSingleton getInstance(Stage stage){
        NavigatorSingleton.instance = new NavigatorSingleton(stage);

        return instance;
    }

    public static synchronized NavigatorSingleton getInstance() {
        return instance;
    }

    public void goTo(String fxml) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(AppStarter.class.getResource(fxml)));
        stage.getScene().setRoot(root);
    }
}
