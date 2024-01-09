package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.SignupApplicativeController;
import com.example.codiceprogetto.logic.bean.SignupBean;
import com.example.codiceprogetto.logic.exception.AlreadyLoggedUserException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.utils.GraphicTool;

import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SignupGraphicController extends GraphicTool{
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passTextField;
    @FXML
    private PasswordField passTextFieldConfirm;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;

    public void loginGUI(MouseEvent mouseEvent) {
        GraphicTool.navigateTo(mouseEvent, "LOGIN");
    }
    public void signUp(MouseEvent mouseEvent) {
        Stage rootToDisplay = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        int ret;
        String errorToDisplay = "Sign up error";

        if(passTextFieldConfirm.getText().equals(passTextField.getText())){
            SignupBean signBean = new SignupBean(emailField.getText(), passTextField.getText(), name.getText(), surname.getText());
            SignupApplicativeController signup = new SignupApplicativeController();

            // check form field syntax
            if(!emailField.getText().isEmpty()) {
                int startIndex = emailField.getText().indexOf('@');
                int endIndex = emailField.getText().indexOf('.', startIndex);
                if(startIndex == -1 || endIndex == -1) {
                    GraphicTool.alert("Wrong email format (****@****.com)", rootToDisplay);
                    cleanUpField();
                }
            }

            try {
                ret = signup.signupUser(signBean);
                if (ret == -1) {
                    GraphicTool.alert(errorToDisplay, rootToDisplay);
                } else if (ret == 1){
                    SessionUser.getInstance().setPage(mouseEvent);
                } else {
                    GraphicTool.alert(errorToDisplay, rootToDisplay);
                }
            } catch (SQLException e) {
                GraphicTool.alert(errorToDisplay, rootToDisplay);
            } catch (EmptyInputException e) {
                GraphicTool.alert(e.getMessage(), rootToDisplay);
            } catch (AlreadyLoggedUserException e) {
                GraphicTool.alert(errorToDisplay, rootToDisplay);
            }
        } else {
            GraphicTool.alert("Inserted passwords doesn't match", rootToDisplay);
            cleanUpField();
        }
    }
    public void cleanUpField() {
        emailField.setText("");
        passTextField.setText("");
        passTextFieldConfirm.setText("");
    }
}
