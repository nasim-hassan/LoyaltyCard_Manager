package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginCustomerController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel; // ✅ Added to show error messages in UI

    @FXML
    public void handleCustomerLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        errorLabel.setText(""); // Clear any previous error

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
            return;
        }

        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/loyaltycard_manager/customer_dashboard.fxml"));
                Parent root = loader.load();

                // Pass user ID to dashboard controller
                CustomerDashboardController controller = loader.getController();
                controller.setCurrentUserId(userId);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Customer Dashboard");
                stage.show();
            } else {
                errorLabel.setText("Invalid username or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Login failed due to system error.");
        }
    }

    @FXML
    public void switchToAdminLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loyaltycard_manager/login_admin.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Login");
        stage.show();
    }

    @FXML
    public void goToSignup(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loyaltycard_manager/signup_customer.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Customer Sign Up");
        stage.show();
    }
}
