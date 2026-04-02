package Models;

/**
 * Keycard Model - Represents a security keycard for bunker access
 */
public class Keycard {
    private int keycardId;
    private int occupantId;
    private String keycardCode;
    private boolean isActive;
    private String issuedAt;

    // Constructor
    public Keycard(int keycardId, int occupantId, String keycardCode, boolean isActive, String issuedAt) {
        this.keycardId = keycardId;
        this.occupantId = occupantId;
        this.keycardCode = keycardCode;
        this.isActive = isActive;
        this.issuedAt = issuedAt;
    }

    // Getters
    public int getKeycardId() {
        return keycardId;
    }

    public int getOccupantId() {
        return occupantId;
    }

    public String getKeycardCode() {
        return keycardCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    // Setters
    public void setActive(boolean active) {
        isActive = active;
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
        return String.format("ID: %d | Code: %s | Occupant ID: %d | Active: %s | Issued: %s",
                keycardId, keycardCode, occupantId, isActive, issuedAt);
    }
}
