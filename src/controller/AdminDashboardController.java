package controller;

import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class AdminDashboardController {

    @FXML
    private Label userCountLabel, activeCardsLabel, todaysTxnLabel;

    @FXML
    private VBox dashboardSummary;

    @FXML
    private VBox usersView;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> colId;
    @FXML
    private TableColumn<User, String> colFullName;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colPhone;
    @FXML
    private TableColumn<User, String> colCreatedAt;

    @FXML
    public void initialize() {
        loadDashboardData();
        setupUserTable();
    }

    private void loadDashboardData() {
        // You can fetch real dashboard stats here from DB,
        // for now, just placeholders

        userCountLabel.setText("120");
        activeCardsLabel.setText("85");
        todaysTxnLabel.setText("30");
    }

    private void setupUserTable() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colFullName.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        colUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colPhone.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        colCreatedAt.setCellValueFactory(cellData -> cellData.getValue().createdAtProperty());
    }

    @FXML
    private void showUsers() {
        dashboardSummary.setVisible(false);
        dashboardSummary.setManaged(false);

        usersView.setVisible(true);
        usersView.setManaged(true);

        loadUsersData();
    }

    @FXML
    private void showDashboard() {
        usersView.setVisible(false);
        usersView.setManaged(false);

        dashboardSummary.setVisible(true);
        dashboardSummary.setManaged(true);
    }

    private void loadUsersData() {
        ObservableList<User> usersList = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, full_name, username, email, phone, created_at FROM users ORDER BY id DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("full_name");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                Timestamp createdAtTS = rs.getTimestamp("created_at");
                String createdAt = createdAtTS != null ? createdAtTS.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "";

                usersList.add(new User(id, fullName, username, email, phone, createdAt));
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        usersTable.setItems(usersList);
    }
}
