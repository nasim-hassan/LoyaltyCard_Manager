package model;

import javafx.beans.property.*;

public class Card {
    private IntegerProperty id;
    private StringProperty cardNumber;
    private StringProperty cardType;
    private DoubleProperty balance;
    private StringProperty username;
    private StringProperty issueDate;
    private StringProperty expiryDate; // ✅ NEW

    public Card(int id, String cardNumber, String cardType, double balance, String username, String issueDate, String expiryDate) {
        this.id = new SimpleIntegerProperty(id);
        this.cardNumber = new SimpleStringProperty(cardNumber);
        this.cardType = new SimpleStringProperty(cardType);
        this.balance = new SimpleDoubleProperty(balance);
        this.username = new SimpleStringProperty(username);
        this.issueDate = new SimpleStringProperty(issueDate);
        this.expiryDate = new SimpleStringProperty(expiryDate); // ✅ NEW
    }

    // ✅ Property Getters (for TableView)
    public IntegerProperty idProperty() { return id; }
    public StringProperty cardNumberProperty() { return cardNumber; }
    public StringProperty cardTypeProperty() { return cardType; }
    public DoubleProperty balanceProperty() { return balance; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty issueDateProperty() { return issueDate; }
    public StringProperty expiryDateProperty() { return expiryDate; }

    // ✅ Optional JavaFX-style value accessors
    public String getExpiryDate() { return expiryDate.get(); }
}
