package com.example.codiceprogetto.logic.utils;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GraphicTool {
    private static Stage stage;
    private static Scene scene;
    public static void navigateTo(MouseEvent e, Parent root){
        stage = (Stage)((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void alert(String messageToDisplay, Stage thisWindow){
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
