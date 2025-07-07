package loyaltycard_manager.service;

import loyaltycard_manager.model.User;
import loyaltycard_manager.util.Session;
import java.util.List;

public class CardService {
    
    // Declare any member variables needed for the service
    private Session session;

    // Constructor to initialize session or other resources if needed
    public CardService() {
        this.session = new Session();
    }
    
    // Example method: Add a card to a user's profile
    public void addCardToUser(User user, String cardDetails) {
        // Logic to add a card to the user's account
        System.out.println("Adding card to user: " + user.getUsername());
        // Assuming you have a Card model, you'd use it here to save details
        // For example:
        // Card card = new Card(cardDetails);
        // user.addCard(card);
    }

    // Example method: Get all cards associated with a user
    public List<String> getCardsForUser(User user) {
        // Logic to fetch user's cards from a database or memory
        System.out.println("Fetching cards for user: " + user.getUsername());
        // Return a list of card details (just an example)
        return List.of("Card 1", "Card 2");  // Replace with actual logic
    }
    
    // Example method: Remove a card from a user's profile
    public void removeCardFromUser(User user, String cardId) {
        // Logic to remove the card from the user's account
        System.out.println("Removing card with ID: " + cardId + " from user: " + user.getUsername());
        // Assuming user.removeCard(cardId) or similar logic to update
    }
    
    // Example method: Update card details for a user
    public void updateCardDetails(User user, String cardId, String newCardDetails) {
        // Logic to update a card for the user
        System.out.println("Updating card with ID: " + cardId + " for user: " + user.getUsername());
        // Example: user.updateCard(cardId, newCardDetails);
    }
    
    // More service methods related to cards can go here
    
    // Example of how you might validate user session (using the Session class)
    public boolean isUserSessionActive(User user) {
        return session.isValid(user);
    }

    // Getters and setters for session or other properties
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
