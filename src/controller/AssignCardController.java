package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import controller.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class AssignCardController {

    @FXML private ComboBox<String> userDropdown;
    @FXML private ComboBox<String> cardTypeDropdown;

    @FXML
    public void initialize() {
        loadUsers();
        loadCardTypes();
    }

    private void loadUsers() {
        ObservableList<String> users = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, username FROM users")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(rs.getInt("id") + " - " + rs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDropdown.setItems(users);
    }

    private void loadCardTypes() {
        ObservableList<String> types = FXCollections.observableArrayList("Silver", "Gold", "Platinum");
        cardTypeDropdown.setItems(types);
    }

    @FXML
    private void assignCard() {
        String userEntry = userDropdown.getValue();
        String cardType = cardTypeDropdown.getValue();

        if (userEntry == null || cardType == null) {
            showAlert("All fields are required.");
            return;
        }

        int userId = Integer.parseInt(userEntry.split(" - ")[0]);
        String cardNumber = "4504" + new Random().nextInt(1000000000);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = """
                INSERT INTO cards (card_number, card_type_id, user_id, balance, issued_by_admin_id)
                VALUES (?, (SELECT id FROM card_types WHERE name = ?), ?, 0, 1)
                """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cardNumber);
            stmt.setString(2, cardType);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
            showAlert("Card assigned successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error assigning card.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
