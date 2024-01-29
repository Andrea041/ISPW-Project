package com.example.codiceprogetto.logic.utils;

import com.example.codiceprogetto.logic.view.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphicTool {
    public void navigateTo(MouseEvent e, String page) {
        Parent root = null;

        try {
            root = switch (page) {
                case "HOME" -> new HomePageView().getHomeView();
                case "LOGIN" -> new LoginView().getLoginView();
                case "SIGNUP" -> new SignupView().getSignupView();
                case "ACC" -> new BrowseAccessoriesView().getAccessoriesView();
                case "CART" -> new ShoppingCartView().getShoppingView();
                case "COBRA" -> new SelectCobraView().getCobraView();
                default -> null;
            };

        } catch (Exception excpetion) {
            Logger.getAnonymousLogger().log(Level.INFO, "Invalid page");
        }

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void alert(String messageToDisplay, Stage thisWindow) {
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
