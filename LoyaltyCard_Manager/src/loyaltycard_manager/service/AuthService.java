package loyaltycard_manager.service;

import loyaltycard_manager.model.User;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User("admin", "admin123", "admin"));
        users.add(new User("john", "1234", "customer"));
    }

    public static User authenticate(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}