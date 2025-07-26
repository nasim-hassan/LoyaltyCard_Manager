package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException; // ✅ Import this
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginAdminController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel; // Make sure this is defined in your FXML

    private static final String DB_URL = "jdbc:mysql://localhost:3306/loyaltycard_manager";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "95Mu0_g,6Ps:";


    @FXML
    private void handleAdminLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
            return;
        }

        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/loyaltycard_manager/admin_dashboard.fxml"));
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Admin Dashboard");
                stage.show();
            } else {
                errorLabel.setText("Invalid username or password.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Database connection error.");
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Login failed due to system error.");
        }
    }

    @FXML
    private void goToCustomerLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loyaltycard_manager/login_customer.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Customer Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Failed to load customer login.");
        }
    }
}
