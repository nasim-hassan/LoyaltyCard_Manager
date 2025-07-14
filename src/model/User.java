package model;

import javafx.beans.property.*;

public class User {

    private final IntegerProperty id;
    private final StringProperty fullName;
    private final StringProperty username;
    private final StringProperty email;
    private final StringProperty phone;
    private final StringProperty createdAt;

    public User(int id, String fullName, String username, String email, String phone, String createdAt) {
        this.id = new SimpleIntegerProperty(id);
        this.fullName = new SimpleStringProperty(fullName);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.createdAt = new SimpleStringProperty(createdAt);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty fullNameProperty() { return fullName; }
    public StringProperty usernameProperty() { return username; }
    public StringProperty emailProperty() { return email; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty createdAtProperty() { return createdAt; }
}
