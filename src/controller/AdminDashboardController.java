// FILE: AdminDashboardController.java

package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import model.Transaction;
import model.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;

public class AdminDashboardController {

    @FXML private Label userCountLabel, activeCardsLabel, todaysTxnLabel;
    @FXML private VBox dashboardSummary, usersView, cardsView, transactionsView, cardAssignView, paymentView;
    @FXML private ComboBox<String> cardTypeComboBox;
    @FXML private TextField userIdField, cardNumberField, invoiceAmountField;
    @FXML private Label priceLabel, discountLabel;

    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, String> colFullName, colUsername, colEmail, colPhone, colCreatedAt;

    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, Integer> colTxnId;
    @FXML private TableColumn<Transaction, String> colCardNumber, colTxnDate, colTxnType, colRemarks;
    @FXML private TableColumn<Transaction, Double> colAmount, colDiscount, colFinalAmount, colBalanceAfter;

    @FXML private TableView<Card> cardsTable;
    @FXML private TableColumn<Card, Integer> colCardId;
    @FXML private TableColumn<Card, String> colCardNumberCard, colCardType, colCardUser, colIssueDate;
    @FXML private TableColumn<Card, Double> colBalance;
    @FXML private TableColumn<Card, String> colExpiryDate;

    @FXML
    public void initialize() {
        loadDashboardData();
        setupUserTable();
        setupTransactionTable();
        setupCardTable();
    }

    private void loadDashboardData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement userStmt = conn.prepareStatement("SELECT COUNT(*) AS total_users FROM users");
            ResultSet userRs = userStmt.executeQuery();
            if (userRs.next()) userCountLabel.setText(String.valueOf(userRs.getInt("total_users")));

            PreparedStatement cardStmt = conn.prepareStatement("SELECT COUNT(*) AS total_cards FROM cards");
            ResultSet cardRs = cardStmt.executeQuery();
            if (cardRs.next()) activeCardsLabel.setText(String.valueOf(cardRs.getInt("total_cards")));

            PreparedStatement txnStmt = conn.prepareStatement("SELECT COUNT(*) AS total_txn FROM transactions WHERE DATE(txn_date) = CURDATE()");
            ResultSet txnRs = txnStmt.executeQuery();
            if (txnRs.next()) todaysTxnLabel.setText(String.valueOf(txnRs.getInt("total_txn")));

        } catch (Exception e) {
            userCountLabel.setText("ERR");
            activeCardsLabel.setText("ERR");
            todaysTxnLabel.setText("ERR");
            e.printStackTrace();
        }
    }

    private void setupUserTable() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colFullName.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        colUsername.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        colEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        colPhone.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        colCreatedAt.setCellValueFactory(cellData -> cellData.getValue().createdAtProperty());
    }

    private void setupTransactionTable() {
        colTxnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        colTxnDate.setCellValueFactory(new PropertyValueFactory<>("txnDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discountApplied"));
        colFinalAmount.setCellValueFactory(new PropertyValueFactory<>("finalAmount"));
        colBalanceAfter.setCellValueFactory(new PropertyValueFactory<>("balanceAfterTxn"));
        colTxnType.setCellValueFactory(new PropertyValueFactory<>("txnType"));
        colRemarks.setCellValueFactory(new PropertyValueFactory<>("remarks"));
    }

    private void setupCardTable() {
        colCardId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCardNumberCard.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        colCardType.setCellValueFactory(new PropertyValueFactory<>("cardType"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colCardUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
    }

    @FXML private void showDashboard() {
        hideAllViews(); dashboardSummary.setVisible(true); dashboardSummary.setManaged(true); loadDashboardData();
    }
    @FXML private void showUsers() {
        hideAllViews(); usersView.setVisible(true); usersView.setManaged(true); loadUsersData();
    }
    @FXML private void showCards() {
        hideAllViews(); cardsView.setVisible(true); cardsView.setManaged(true); loadCardsData();
    }
    @FXML private void showTransactions() {
        hideAllViews(); transactionsView.setVisible(true); transactionsView.setManaged(true); loadTransactionsData();
    }
    @FXML private void showCardAssign() {
        hideAllViews(); cardAssignView.setVisible(true); cardAssignView.setManaged(true); loadCardTypes();
    }
    @FXML private void showPayment() {
        hideAllViews(); paymentView.setVisible(true); paymentView.setManaged(true);
    }

    private void hideAllViews() {
        dashboardSummary.setVisible(false); dashboardSummary.setManaged(false);
        usersView.setVisible(false); usersView.setManaged(false);
        cardsView.setVisible(false); cardsView.setManaged(false);
        transactionsView.setVisible(false); transactionsView.setManaged(false);
        cardAssignView.setVisible(false); cardAssignView.setManaged(false);
        paymentView.setVisible(false); paymentView.setManaged(false);
    }

    @FXML
    private void logout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/loyaltycard_manager/login_customer.fxml"));
            Stage stage = (Stage) userCountLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Customer Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadUsersData() {
        ObservableList<User> usersList = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, full_name, username, email, phone, created_at FROM users ORDER BY id DESC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("full_name");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String createdAt = rs.getTimestamp("created_at").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                usersList.add(new User(id, fullName, username, email, phone, createdAt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        usersTable.setItems(usersList);
    }

    private void loadTransactionsData() {
        ObservableList<Transaction> txnList = FXCollections.observableArrayList();
        String query = "SELECT t.id, c.card_number, t.txn_date, t.amount, t.discount_applied, t.final_amount, t.balance_after_txn, t.txn_type, t.remarks FROM transactions t JOIN cards c ON t.card_id = c.id ORDER BY t.txn_date DESC";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                txnList.add(new Transaction(
                    rs.getInt("id"), rs.getString("card_number"),
                    rs.getTimestamp("txn_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    rs.getDouble("amount"), rs.getDouble("discount_applied"), rs.getDouble("final_amount"),
                    rs.getDouble("balance_after_txn"), rs.getString("txn_type"), rs.getString("remarks")
                ));
            }
            transactionsTable.setItems(txnList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCardsData() {
        ObservableList<Card> cardList = FXCollections.observableArrayList();
        String query = "SELECT c.id, c.card_number, ct.name AS type, c.balance, u.username, c.issue_date, c.expiry_date " +
                       "FROM cards c " +
                       "JOIN card_types ct ON c.card_type_id = ct.id " +
                       "JOIN users u ON c.user_id = u.id " +
                       "ORDER BY c.issue_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cardList.add(new Card(
                    rs.getInt("id"),
                    rs.getString("card_number"),
                    rs.getString("type"),
                    rs.getDouble("balance"),
                    rs.getString("username"),
                    rs.getTimestamp("issue_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    rs.getTimestamp("expiry_date") != null
                        ? rs.getTimestamp("expiry_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        : "-"
                ));
            }

            cardsTable.setItems(cardList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadCardTypes() {
        cardTypeComboBox.getItems().clear();
        cardTypeComboBox.getItems().addAll("Silver", "Gold", "Platinum");
        cardTypeComboBox.setOnAction(event -> {
            switch (cardTypeComboBox.getValue()) {
                case "Silver" -> { priceLabel.setText("10.00"); discountLabel.setText("5%"); }
                case "Gold" -> { priceLabel.setText("15.00"); discountLabel.setText("10%"); }
                case "Platinum" -> { priceLabel.setText("20.00"); discountLabel.setText("15%"); }
                default -> { priceLabel.setText("-"); discountLabel.setText("-"); }
            }
        });
    }

    @FXML
    private void handleAssignCard() {
        String userIdText = userIdField.getText();
        String cardType = cardTypeComboBox.getValue();

        if (userIdText.isEmpty() || cardType == null) {
            showAlert("Please enter User ID and select Card Type.");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdText);

            // Get balance based on type
            double balanceToAdd = switch (cardType) {
                case "Silver" -> 5000.00;
                case "Gold" -> 10000.00;
                case "Platinum" -> 15000.00;
                default -> 0.0;
            };

            Connection conn = DatabaseConnection.getConnection();

            // Get card_type_id
            PreparedStatement typeStmt = conn.prepareStatement("SELECT id FROM card_types WHERE name = ?");
            typeStmt.setString(1, cardType);
            ResultSet typeRs = typeStmt.executeQuery();
            if (!typeRs.next()) {
                showAlert("Invalid card type.");
                return;
            }
            int cardTypeId = typeRs.getInt("id");

            // Check if user already has same type card
            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT id, balance FROM cards WHERE user_id = ? AND card_type_id = ?");
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, cardTypeId);
            ResultSet existingCardRs = checkStmt.executeQuery();

            if (existingCardRs.next()) {
                // 🔁 RENEW existing card
                int cardId = existingCardRs.getInt("id");
                double currentBalance = existingCardRs.getDouble("balance");
                double updatedBalance = currentBalance + balanceToAdd;

                PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE cards SET balance = ?, expiry_date = DATE_ADD(CURDATE(), INTERVAL 1 YEAR) WHERE id = ?");
                updateStmt.setDouble(1, updatedBalance);
                updateStmt.setInt(2, cardId);
                int updated = updateStmt.executeUpdate();

                if (updated > 0) {
                    showAlert("Card renewed successfully. Balance adjusted.");
                    showCards();
                } else {
                    showAlert("Renewal failed.");
                }

            } else {
                // 🆕 NEW card assignment
                long random12Digits = (long)(Math.random() * 1_000_000_000_000L);
                String cardNumber = "4504" + String.format("%012d", random12Digits);

                String insert = "INSERT INTO cards (card_number, card_type_id, user_id, balance, issue_date, expiry_date) " +
                                "VALUES (?, ?, ?, ?, NOW(), DATE_ADD(CURDATE(), INTERVAL 1 YEAR))";
                PreparedStatement insertStmt = conn.prepareStatement(insert);
                insertStmt.setString(1, cardNumber);
                insertStmt.setInt(2, cardTypeId);
                insertStmt.setInt(3, userId);
                insertStmt.setDouble(4, balanceToAdd);

                int rows = insertStmt.executeUpdate();
                showAlert(rows > 0 ? "Card assigned successfully." : "Card assignment failed.");
                showCards();
            }

        } catch (NumberFormatException e) {
            showAlert("User ID must be a valid number.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error assigning card.");
        }
    }


    @FXML
    private void handlePayment() {
        String cardNumber = cardNumberField.getText();
        String amountText = invoiceAmountField.getText();

        if (cardNumber.isEmpty() || amountText.isEmpty()) {
            showAlert("Please fill in all fields.");
            return;
        }

        try {
            double invoiceAmount = Double.parseDouble(amountText);
            Connection conn = DatabaseConnection.getConnection();

            // 1. Get card info
            PreparedStatement cardStmt = conn.prepareStatement(
                    "SELECT c.id, c.balance, ct.discount_percentage " +
                    "FROM cards c " +
                    "JOIN card_types ct ON c.card_type_id = ct.id " +
                    "WHERE c.card_number = ?");
            cardStmt.setString(1, cardNumber);
            ResultSet rs = cardStmt.executeQuery();

            if (!rs.next()) {
                showAlert("Card not found.");
                return;
            }

            int cardId = rs.getInt("id");
            double currentBalance = rs.getDouble("balance");
            double discountPercentage = rs.getDouble("discount_percentage");

            // 2. Calculate discount
            double discount = (invoiceAmount * discountPercentage) / 100;
            double finalAmount = invoiceAmount - discount;

            if (currentBalance < finalAmount) {
                showAlert("Insufficient balance after applying discount.");
                return;
            }

            double newBalance = currentBalance - finalAmount;

            // 3. Update balance
            PreparedStatement updateStmt = conn.prepareStatement("UPDATE cards SET balance = ? WHERE id = ?");
            updateStmt.setDouble(1, newBalance);
            updateStmt.setInt(2, cardId);
            updateStmt.executeUpdate();

            // 4. Record transaction
            PreparedStatement txnStmt = conn.prepareStatement(
                    "INSERT INTO transactions (card_id, amount, discount_applied, final_amount, balance_after_txn, txn_type, remarks) " +
                    "VALUES (?, ?, ?, ?, ?, 'Purchase', 'Invoice Payment')");
            txnStmt.setInt(1, cardId);
            txnStmt.setDouble(2, invoiceAmount);
            txnStmt.setDouble(3, discount);
            txnStmt.setDouble(4, finalAmount);
            txnStmt.setDouble(5, newBalance);
            txnStmt.executeUpdate();

            showAlert("Payment successful. Discount Applied: ৳" + discount);
            showCards();

        } catch (NumberFormatException e) {
            showAlert("Invalid amount format.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Payment failed due to system error.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
