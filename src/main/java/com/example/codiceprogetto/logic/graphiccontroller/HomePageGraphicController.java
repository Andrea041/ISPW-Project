package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.exception.NotLoggedUserException;
import com.example.codiceprogetto.logic.utils.Utilities;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class HomePageGraphicController extends Utilities {
    @FXML
    private ImageView manImage;

    public void manZoomEnter() {
        zoomIN(manImage);
    }
    public void manZoomExit() {
        zoomOUT(manImage);
    }

    @FXML
    private ImageView womanPic;
    public void womZoomEnter() {
        zoomIN(womanPic);
    }
    public void womZoomExit() {
        zoomOUT(womanPic);
    }

    @FXML
    private ImageView accImage;
    public void accessorGUI() {
        navigateTo(ACC);
    }

    public void accZoomEnter() {
        zoomIN(accImage);
    }
    public void accZoomExit() {
        zoomOUT(accImage);
    }

    @FXML
    private ImageView magazinePic;
    public void magZoomEnter() {
        zoomIN(magazinePic);
    }
    public void magZoomExit() {
        zoomOUT(magazinePic);
    }

    public void accountGUI(){
        try {
            logoutUser();
            navigateTo(HOME);
        } catch (NotLoggedUserException e) {
            alert("You are not logged in!");
        }
    }

    public void cartGUI() {
        navigateTo(CART);
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

    public void login() {
        navigateTo(LOGIN);
    }
}