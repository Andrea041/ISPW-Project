package com.example.codiceprogetto.LoggingForm;

import com.example.codiceprogetto.utils.GraphicTool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignupGraphicController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passTextField;
    @FXML
    private PasswordField passTextFieldConfirm;
    public void loginGUI(MouseEvent mouseEvent) throws Exception {
        Parent loginView = new LoginView().getLoginView();
        GraphicTool.navigateTo(mouseEvent, loginView);
    }
    public void signUp(ActionEvent actionEvent) {
        Stage rootToDisplay = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        if(passTextFieldConfirm.getText().equals(passTextField.getText()) && !passTextField.getText().isEmpty() && !passTextFieldConfirm.getText().isEmpty()){
            SignupBean signBean = new SignupBean(emailField.getText(), passTextField.getText());
            SignupApplicativeController signup = new SignupApplicativeController(signBean);

            // check for user type by the email ****@customer.com or ****@seller.com
            int startIndex = emailField.getText().indexOf('@');
            int endIndex = emailField.getText().indexOf('.', startIndex);
            if(startIndex == -1 || endIndex == -1) {
                GraphicTool.alert("Wrong email format (****@customer/seller.com)", rootToDisplay);
                cleanUpField();
            }
            else {
                String substring = emailField.getText().substring(startIndex + 1, endIndex);
                if (substring.equals("customer"))
                    signBean.setUserType("CUSTOMER");
                else if (substring.equals("seller"))
                    signBean.setUserType("SELLER");
                else {
                    GraphicTool.alert("Wrong email format (****@customer/seller.com)", rootToDisplay);
                    cleanUpField();
                }
                signup.signupUser();
            }
        } else if(passTextField.getText().isEmpty() || passTextFieldConfirm.getText().isEmpty() || emailField.getText().isEmpty()) {
            GraphicTool.alert("Fields must not be empty", rootToDisplay);
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
