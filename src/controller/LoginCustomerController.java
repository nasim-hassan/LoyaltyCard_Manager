package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

public class LoginCustomerController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void handleCustomerLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Basic dummy login check (customize as needed)
        if (username.equals("customer") && password.equals("1234")) {
            System.out.println("Customer logged in!");
            // You can load the customer dashboard here
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    public void switchToAdminLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loyaltycard_manager/login_admin.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Login");
        stage.show();
    }
}
