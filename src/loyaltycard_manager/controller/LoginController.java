package loyaltycard_manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import loyaltycard_manager.model.User;
import loyaltycard_manager.service.AuthService;
import loyaltycard_manager.util.Session;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void handleLogin() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = AuthService.authenticate(username, password);

        if (user != null) {
            Session.currentUser = user;
            String view = user.getRole().equals("admin") ? "admin_dashboard.fxml" : "customer_dashboard.fxml";

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/loyaltycard_manager/view/" + view))));
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid login").show();
        }
    }
}