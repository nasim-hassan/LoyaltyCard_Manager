package loyaltycard_manager.model;

public class LoyaltyCard {
    private String customerName;
    private int points;

    public LoyaltyCard(String customerName, int points) {
        this.customerName = customerName;
        this.points = points;
    }

    public String getCustomerName() { return customerName; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}