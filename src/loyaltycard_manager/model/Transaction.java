package loyaltycard_manager.model;

import java.time.LocalDate;

public class Transaction {
    private String detail;
    private int pointsChanged;
    private LocalDate date;

    public Transaction(String detail, int pointsChanged, LocalDate date) {
        this.detail = detail;
        this.pointsChanged = pointsChanged;
        this.date = date;
    }

    public String getDetail() { return detail; }
    public int getPointsChanged() { return pointsChanged; }
    public LocalDate getDate() { return date; }
}