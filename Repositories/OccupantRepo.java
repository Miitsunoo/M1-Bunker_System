package Repositories;

import Models.Occupant;
import java.util.ArrayList;
import java.util.List;

/**
 * OccupantRepo - Hardcoded occupant data storage
 */
public class OccupantRepo {
    private static List<Occupant> occupants = new ArrayList<>();

    // Static initializer - loads hardcoded occupant data
    static {
        occupants.add(new Occupant(1, "Alexander", "Stone", "Commander", "101"));
        occupants.add(new Occupant(2, "Sarah", "Mitchell", "Staff Officer", "102"));
        occupants.add(new Occupant(3, "David", "Chen", "Technician", "103"));
        occupants.add(new Occupant(4, "Emma", "Rodriguez", "Medic", "104"));
        occupants.add(new Occupant(5, "James", "Thompson", "Guard", "105"));
        occupants.add(new Occupant(6, "Lisa", "Anderson", "Scientist", "106"));
        occupants.add(new Occupant(7, "Michael", "Jackson", "Engineer", "107"));
        occupants.add(new Occupant(8, "Rachel", "Williams", "Administrator", "108"));
        occupants.add(new Occupant(9, "Kevin", "Davis", "Support Staff", "109"));
        occupants.add(new Occupant(10, "Jessica", "Martinez", "Technician", "110"));
    }

    /**
     * Get all occupants
     */
    public static List<Occupant> getAllOccupants() {
        return new ArrayList<>(occupants);
    }

    /**
     * Get occupant by ID
     */
    public static Occupant getOccupantById(int id) {
        for (Occupant occupant : occupants) {
            if (occupant.getId() == id) {
                return occupant;
            }
        }
        return null;
    }

    /**
     * Get occupant by first and last name
     */
    public static Occupant getOccupantByName(String firstName, String lastName) {
        for (Occupant occupant : occupants) {
            if (occupant.getFirstName().equalsIgnoreCase(firstName) &&
                occupant.getLastName().equalsIgnoreCase(lastName)) {
                return occupant;
            }
        }
        return null;
    }

    /**
     * Verify occupant exists by ID, first name, and last name
     */
    public static boolean verifyOccupant(String firstName, String lastName, int occupantId) {
        Occupant occupant = getOccupantById(occupantId);
        if (occupant == null) {
            return false;
        }
        return occupant.getFirstName().equalsIgnoreCase(firstName) &&
               occupant.getLastName().equalsIgnoreCase(lastName);
    }
}
