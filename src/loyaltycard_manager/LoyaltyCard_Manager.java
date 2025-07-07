package loyaltycard_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoyaltyCard_Manager extends Application {

    public static void main(String[] args) {
        launch(args);  // This calls the start method below
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String fxmlPath = "view/admin_dashboard.fxml";
        System.out.println("Loading FXML from: " + fxmlPath);

        if (getClass().getResource(fxmlPath) == null) {
            System.out.println("ERROR: FXML file not found at " + fxmlPath);
            return;  // exit if the file is not found
        }

        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        primaryStage.setTitle("Loyalty Card Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
