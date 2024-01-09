package com.example.codiceprogetto.logic.utils;

import com.example.codiceprogetto.logic.view.HomePageView;
import com.example.codiceprogetto.logic.view.LoginView;
import com.example.codiceprogetto.logic.view.SignupView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GraphicTool {
    private static Stage stage;
    private static Scene scene;
    public static void navigateTo(MouseEvent e, String page) {
        Parent root = null;

        try {
            switch (page) {
                case "HOME":
                    root = new HomePageView().getHomeView();
                    break;
                case "LOGIN":
                    root = new LoginView().getLoginView();
                    break;
                case "SIGNUP":
                    root = new SignupView().getSignupView();
                    break;
                default:
                    root = null;
                    break;
            }
        } catch (Exception excpetion) {
            Logger.getAnonymousLogger().log(Level.INFO, "Invalid page");
        }

        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void alert(String messageToDisplay, Stage thisWindow) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(messageToDisplay);

        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: lightgray; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold");

        Label contentText = (Label) alert.getDialogPane().lookup(".content");
        contentText.setStyle("-fx-font-size: 16px;");

        alert.initOwner(thisWindow);
        alert.showAndWait();
    }
}
