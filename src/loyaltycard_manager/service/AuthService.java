package loyaltycard_manager.service;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    
    // In real-world scenarios, this should query the database.
    private static final Map<String, String> mockDatabase = new HashMap<>();
    
    static {
        // Sample users (username -> password)
        mockDatabase.put("adminUser", "admin123");
        mockDatabase.put("customerUser1", "customer123");
    }

    public boolean authenticateUser(String username, String password) {
        // Check if username exists and if the password matches
        String storedPassword = mockDatabase.get(username);
        
        // Check password (plain text for demo; use hashed password in production)
        return storedPassword != null && storedPassword.equals(password);
    }
}

