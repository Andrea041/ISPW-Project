package com.example.codiceprogetto.logic.utils;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GraphicTool {
    protected static final String HOME = "/com/example/codiceprogetto/FXML/HomePage/HomePage.fxml";
    protected static final String LOGIN = "/com/example/codiceprogetto/FXML/LoggingForm/LoginForm.fxml";
    protected static final String SIGNUP = "/com/example/codiceprogetto/FXML/LoggingForm/SignUpForm.fxml";
    protected static final String ACC = "/com/example/codiceprogetto/FXML/BrowseAccessories/BrowseProduct.fxml";
    protected static final String CART = "/com/example/codiceprogetto/FXML/ShoppingCart/ShoppingCart.fxml";
    protected static final String COBRA = "/com/example/codiceprogetto/FXML/SelectCobra/SelectCobra.fxml";
    protected static final String CHECKOUT = "/com/example/codiceprogetto/FXML/Checkout/Checkout.fxml";
    protected static final String PAY = "/com/example/codiceprogetto/FXML/Payment/Payment.fxml";
    protected static final String PAYSUM = "/com/example/codiceprogetto/FXML/Payment/PaymentSummary.fxml";
    protected static final String ORDER = "/com/example/codiceprogetto/FXML/IncomingOrder/IncomingOrder.fxml";

    protected void navigateTo(String page) {
        try {
            NavigatorSingleton.getInstance().goTo(page);
        } catch(IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "Invalid page" + e);
        }
    }

    protected void navigateToAlternative(Parent root) {
        NavigatorSingleton.getInstance().goToAlternative(root);
    }

    public void alert(String messageToDisplay) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(messageToDisplay);

        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: lightgray; -fx-padding: 10px; -fx-font-family: 'Arial'; -fx-text-fill: black; -fx-font-size: 20px; -fx-font-weight: bold");

        Label contentText = (Label) alert.getDialogPane().lookup(".content");
        contentText.setStyle("-fx-font-size: 16px;");

        alert.initOwner(NavigatorSingleton.getInstance().getStage());
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
