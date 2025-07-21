package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Card;
import model.Transaction;
import controller.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;

public class CustomerDashboardController {

    // UI Elements
    @FXML private VBox dashboardView, myCardView, transactionsView;
    @FXML private Label totalCardsLabel, balanceLabel, monthlySpendsLabel;
    @FXML private TableView<Card> cardsTable;
    @FXML private TableColumn<Card, String> colCardNumber, colCardType, colIssueDate;
    @FXML private TableColumn<Card, Double> colBalance;

    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, String> colTxnCardNumber, colTxnDate, colTxnType, colTxnRemarks;
    @FXML private TableColumn<Transaction, Double> colTxnAmount, colTxnDiscount, colTxnFinalAmount;

    private int currentUserId;

    // Set from login controller
    public void setCurrentUserId(int userId) {
        this.currentUserId = userId;
        showDashboard(); // 🚀 Load dashboard immediately
    }

    @FXML
    public void showDashboard() {
        showView(dashboardView);
        loadDashboardData();
    }

    @FXML
    public void showMyCards() {
        showView(myCardView);
        loadCardData();
    }

    @FXML
    public void showTransactions() {
        showView(transactionsView);
        loadTransactionData();
    }

    private void showView(VBox view) {
        dashboardView.setVisible(false); dashboardView.setManaged(false);
        myCardView.setVisible(false); myCardView.setManaged(false);
        transactionsView.setVisible(false); transactionsView.setManaged(false);

        view.setVisible(true); view.setManaged(true);
    }

    private void loadDashboardData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Total cards and balance
            String sql = """
                SELECT COUNT(*) AS card_count, 
                       COALESCE(SUM(balance), 0) AS total_balance 
                FROM cards 
                WHERE user_id = ?
            """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentUserId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalCardsLabel.setText(String.valueOf(rs.getInt("card_count")));
                balanceLabel.setText("৳" + rs.getDouble("total_balance"));
            }

            // Monthly spend
            String spendSql = """
                SELECT COALESCE(SUM(final_amount), 0) AS monthly_spend
                FROM transactions t
                JOIN cards c ON t.card_id = c.id
                WHERE c.user_id = ? AND MONTH(t.txn_date) = MONTH(CURDATE())
            """;
            PreparedStatement spendStmt = conn.prepareStatement(spendSql);
            spendStmt.setInt(1, currentUserId);
            ResultSet spendRs = spendStmt.executeQuery();
            if (spendRs.next()) {
                monthlySpendsLabel.setText("৳" + spendRs.getDouble("monthly_spend"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCardData() {
        ObservableList<Card> cardList = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = """
                SELECT c.card_number, ct.name AS type, c.balance, c.issue_date
                FROM cards c
                JOIN card_types ct ON c.card_type_id = ct.id
                WHERE c.user_id = ?
                ORDER BY c.issue_date DESC
            """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentUserId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String number = rs.getString("card_number");
                String type = rs.getString("type");
                double balance = rs.getDouble("balance");
                String issued = rs.getTimestamp("issue_date").toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                cardList.add(new Card(0, number, type, balance, "", issued));
            }

            colCardNumber.setCellValueFactory(cell -> cell.getValue().cardNumberProperty());
            colCardType.setCellValueFactory(cell -> cell.getValue().cardTypeProperty());
            colBalance.setCellValueFactory(cell -> cell.getValue().balanceProperty().asObject());
            colIssueDate.setCellValueFactory(cell -> cell.getValue().issueDateProperty());

            cardsTable.setItems(cardList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTransactionData() {
        ObservableList<Transaction> txnList = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = """
                SELECT c.card_number, t.txn_date, t.amount, t.discount_applied, 
                       t.final_amount, t.txn_type, t.remarks
                FROM transactions t
                JOIN cards c ON t.card_id = c.id
                WHERE c.user_id = ?
                ORDER BY t.txn_date DESC
            """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, currentUserId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String number = rs.getString("card_number");
                String date = rs.getTimestamp("txn_date").toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                double amount = rs.getDouble("amount");
                double discount = rs.getDouble("discount_applied");
                double finalAmt = rs.getDouble("final_amount");
                String type = rs.getString("txn_type");
                String remarks = rs.getString("remarks");

                txnList.add(new Transaction(0, number, date, amount, discount, finalAmt, 0.0, type, remarks));
            }

            colTxnCardNumber.setCellValueFactory(cell -> cell.getValue().cardNumberProperty());
            colTxnDate.setCellValueFactory(cell -> cell.getValue().txnDateProperty());
            colTxnAmount.setCellValueFactory(cell -> cell.getValue().amountProperty().asObject());
            colTxnDiscount.setCellValueFactory(cell -> cell.getValue().discountAppliedProperty().asObject());
            colTxnFinalAmount.setCellValueFactory(cell -> cell.getValue().finalAmountProperty().asObject());
            colTxnType.setCellValueFactory(cell -> cell.getValue().txnTypeProperty());
            colTxnRemarks.setCellValueFactory(cell -> cell.getValue().remarksProperty());

            transactionsTable.setItems(txnList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/loyaltycard_manager/login_customer.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) totalCardsLabel.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Customer Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
