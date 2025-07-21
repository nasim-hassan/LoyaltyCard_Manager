package model;

import javafx.beans.property.*;

public class Transaction {
    private IntegerProperty id;
    private StringProperty cardNumber;
    private StringProperty txnDate;
    private DoubleProperty amount;
    private DoubleProperty discountApplied;
    private DoubleProperty finalAmount;
    private DoubleProperty balanceAfterTxn;
    private StringProperty txnType;
    private StringProperty remarks;

    public Transaction(int id, String cardNumber, String txnDate, double amount,
                       double discountApplied, double finalAmount, double balanceAfterTxn,
                       String txnType, String remarks) {
        this.id = new SimpleIntegerProperty(id);
        this.cardNumber = new SimpleStringProperty(cardNumber);
        this.txnDate = new SimpleStringProperty(txnDate);
        this.amount = new SimpleDoubleProperty(amount);
        this.discountApplied = new SimpleDoubleProperty(discountApplied);
        this.finalAmount = new SimpleDoubleProperty(finalAmount);
        this.balanceAfterTxn = new SimpleDoubleProperty(balanceAfterTxn);
        this.txnType = new SimpleStringProperty(txnType);
        this.remarks = new SimpleStringProperty(remarks);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty cardNumberProperty() { return cardNumber; }
    public StringProperty txnDateProperty() { return txnDate; }
    public DoubleProperty amountProperty() { return amount; }
    public DoubleProperty discountAppliedProperty() { return discountApplied; }
    public DoubleProperty finalAmountProperty() { return finalAmount; }
    public DoubleProperty balanceAfterTxnProperty() { return balanceAfterTxn; }
    public StringProperty txnTypeProperty() { return txnType; }
    public StringProperty remarksProperty() { return remarks; }
}
