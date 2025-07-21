package loyaltycard_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class LoyaltyCard_Manager extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login_customer.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("admin_dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Customer Signup");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
