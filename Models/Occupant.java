package Models;

/**
 * Occupant Model - Represents a bunker occupant
 */
public class Occupant {
    private int occupantId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String registeredAt;

    // Constructor
    public Occupant(int occupantId, String firstName, String lastName, String email, String phone, String registeredAt) {
        this.occupantId = occupantId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.registeredAt = registeredAt;
    }

    // Getters
    public int getOccupantId() {
        return occupantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    // Additional getters
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getId() {
        return occupantId;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s %s | Email: %s | Phone: %s | Registered: %s",
                occupantId, firstName, lastName, email, phone, registeredAt);
    }
}
