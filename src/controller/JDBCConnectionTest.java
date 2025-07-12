package controller;

import java.sql.Connection;

public class JDBCConnectionTest {

    public static void main(String[] args) {
        // Test database connection
        Connection connection = DatabaseConnection.getConnection();
        
        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}
