package Models;

/**
 * Keycard Model - Represents a security keycard for bunker access
 */
public class Keycard {
    private int id;
    private String cardNumber;
    private int accessLevel;  // 1=Guest, 2=Standard, 3=Staff, 4=Admin
    private int occupantId;   // Links to Occupant
    private boolean isActive;
    private String issueDate;

    // Constructor
    public Keycard(int id, String cardNumber, int accessLevel, int occupantId, boolean isActive, String issueDate) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.accessLevel = accessLevel;
        this.occupantId = occupantId;
        this.isActive = isActive;
        this.issueDate = issueDate;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public String getAccessLevelName() {
        switch(accessLevel) {
            case 1: return "Guest";
            case 2: return "Standard";
            case 3: return "Staff";
            case 4: return "Admin";
            default: return "Unknown";
        }
    }

    public int getOccupantId() {
        return occupantId;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getIssueDate() {
        return issueDate;
    }

    // Setters
    public void setActive(boolean active) {
        isActive = active;
    }

    public void setAccessLevel(int level) {
        this.accessLevel = level;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Card: %s | Level: %s | Occupant ID: %d | Active: %s | Issued: %s",
                id, cardNumber, getAccessLevelName(), occupantId, isActive, issueDate);
    }
}
