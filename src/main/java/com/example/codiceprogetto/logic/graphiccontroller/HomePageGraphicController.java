package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.utils.GraphicTool;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomePageGraphicController extends GraphicTool {
    @FXML
    private ImageView manImage;
    public void manSwimGUI(MouseEvent mouseEvent) {
        System.out.println("try");
    }
    public void manZoomEnter(MouseEvent mouseEvent) {
        zoomIN(manImage);
    }
    public void manZoomExit(MouseEvent mouseEvent) {
        zoomOUT(manImage);
    }

    @FXML
    private ImageView womanPic;
    public void womSwimGUI(MouseEvent mouseEvent) {
        System.out.println("try");
    }
    public void womZoomEnter(MouseEvent mouseEvent) {
        zoomIN(womanPic);
    }
    public void womZoomExit(MouseEvent mouseEvent) {
        zoomOUT(womanPic);
    }

    @FXML
    private ImageView accImage;
    public void accessorGUI(MouseEvent mouseEvent) {
        GraphicTool.navigateTo(mouseEvent, "ACC");
    }

    public void accZoomEnter(MouseEvent mouseEvent) {
        zoomIN(accImage);
    }
    public void accZoomExit(MouseEvent mouseEvent) {
        zoomOUT(accImage);
    }

    @FXML
    private ImageView magazinePic;
    public void magZoomEnter(MouseEvent mouseEvent) {
        zoomIN(magazinePic);
    }
    public void magZoomExit(MouseEvent mouseEvent) {
        zoomOUT(magazinePic);
    }

    public void accountGUI(MouseEvent mouseEvent){
        System.out.println("try");
    }

    public void cartGUI(MouseEvent mouseEvent) {
        GraphicTool.navigateTo(mouseEvent, "CART");
    }

    public void zoomIN(ImageView photo){
        ScaleTransition transition = new ScaleTransition(Duration.millis(500), photo);
        transition.setFromX(1.0);
        transition.setFromY(1.0);
        transition.setToX(1.1);
        transition.setToY(1.1);

        transition.playFromStart();
    }

    public void zoomOUT(ImageView photo){
        ScaleTransition transition = new ScaleTransition(Duration.millis(500), photo);
        transition.setFromX(1.1);
        transition.setFromY(1.1);
        transition.setToX(1.0);
        transition.setToY(1.0);

        transition.playFromStart();
    }
}