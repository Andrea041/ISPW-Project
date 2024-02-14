package com.example.codiceprogetto.logic.utils;

import com.example.codiceprogetto.logic.view.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GraphicTool {
    // Implementare un navigator singleton molto meglio!
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
                case "CHECKOUT" -> new CheckoutView().getCheckoutView();
                case "PAY" -> new PaymentView().getPaymentView();
                default -> null;
            };

        } catch (Exception exception) {
            Logger.getAnonymousLogger().log(Level.INFO, "Invalid page");
        }

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void alert(String messageToDisplay, Stage thisWindow) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(messageToDisplay);

        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: lightgray; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold");

        Label contentText = (Label) alert.getDialogPane().lookup(".content");
        contentText.setStyle("-fx-font-size: 16px;");

        alert.initOwner(thisWindow);
        alert.showAndWait();
    }

    public boolean displayConfirmBox(String message, String yesBut, String noBut) {
        Stage confirmBox = new Stage();
        AtomicBoolean answer = new AtomicBoolean(false);

        confirmBox.initModality(Modality.APPLICATION_MODAL);
        confirmBox.setTitle("Alert");

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        layout.getChildren().add(new javafx.scene.control.Label(message));

        Button yesButton = new Button(yesBut);
        Button noButton = new Button(noBut);

        yesButton.setOnAction(e -> {
            answer.set(true);
            confirmBox.close();
        });

        noButton.setOnAction(e -> {
            answer.set(false);
            confirmBox.close();
        });

        buttonBox.getChildren().addAll(yesButton, noButton);
        layout.getChildren().add(buttonBox);

        Scene scene = new Scene(layout, 300, 150);
        confirmBox.setScene(scene);
        confirmBox.showAndWait();

        return answer.get();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
