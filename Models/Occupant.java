package Models;

/**
 * Occupant - Represents a person living/working in the bunker
 */
public class Occupant {
    private int id;
    private String firstName;
    private String lastName;
    private String position;
    private String roomNumber;

    public Occupant(int id, String firstName, String lastName, String position, String roomNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.roomNumber = roomNumber;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Position: %s | Room: %s",
                id, getFullName(), position, roomNumber);
    }
}
