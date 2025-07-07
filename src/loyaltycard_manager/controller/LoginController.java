package loyaltycard_manager.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import loyaltycard_manager.service.AuthService;

public class LoginController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    
    private AuthService authService;

    public LoginController() {
        this.authService = new AuthService();
    }

    @FXML
    public void handleLogin(MouseEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        // Simple validation (You can enhance this)
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
            errorLabel.setVisible(true);
            return;
        }
        
        boolean isAuthenticated = authService.authenticateUser(username, password);
        
        if (isAuthenticated) {
            // If authenticated, proceed to the main dashboard (Admin or Customer)
            // For now, let's just print a success message
            System.out.println("Login successful!");
            
            // You can load a new scene here or redirect to the dashboard
            // e.g., loadDashboardScene();
        } else {
            errorLabel.setText("Invalid username or password.");
            errorLabel.setVisible(true);
        }
    }
}
