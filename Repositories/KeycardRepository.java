package Repositories;

import Models.Keycard;
import java.util.ArrayList;
import java.util.List;

/**
 * KeycardRepository - Hardcoded keycard data storage
 */
public class KeycardRepository {
    private static List<Keycard> keycards = new ArrayList<>();

    // Static initializer - loads hardcoded data
    static {
        keycards.add(new Keycard(1, "KC-2401-001", 4, 1, true, "2024-01-15"));
        keycards.add(new Keycard(2, "KC-2401-002", 3, 2, true, "2024-02-10"));
        keycards.add(new Keycard(3, "KC-2401-003", 2, 3, true, "2024-02-20"));
        keycards.add(new Keycard(4, "KC-2401-004", 2, 4, true, "2024-03-01"));
        keycards.add(new Keycard(5, "KC-2401-005", 1, 5, true, "2024-03-10"));
        keycards.add(new Keycard(6, "KC-2401-006", 3, 6, true, "2024-03-15"));
        keycards.add(new Keycard(7, "KC-2401-007", 2, 7, false, "2024-01-20"));
        keycards.add(new Keycard(8, "KC-2401-008", 4, 8, true, "2024-02-05"));
        keycards.add(new Keycard(9, "KC-2401-009", 1, 9, true, "2024-03-20"));
        keycards.add(new Keycard(10, "KC-2401-010", 3, 10, true, "2024-03-01"));
    }

    /**
     * Get all keycards
     */
    public static List<Keycard> getAllKeycards() {
        return new ArrayList<>(keycards);
    }

    /**
     * Get keycard by ID
     */
    public static Keycard getKeycardById(int id) {
        for (Keycard card : keycards) {
            if (card.getId() == id) {
                return card;
            }
        }
        return null;
    }

    /**
     * Get keycard by card number
     */
    public static Keycard getKeycardByNumber(String cardNumber) {
        for (Keycard card : keycards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }
        return null;
    }

    /**
     * Get all keycards by occupant ID
     */
    public static List<Keycard> getKeycardsByOccupantId(int occupantId) {
        List<Keycard> result = new ArrayList<>();
        for (Keycard card : keycards) {
            if (card.getOccupantId() == occupantId) {
                result.add(card);
            }
        }
        return result;
    }

    /**
     * Get all active keycards
     */
    public static List<Keycard> getAllActiveKeycards() {
        List<Keycard> result = new ArrayList<>();
        for (Keycard card : keycards) {
            if (card.isActive()) {
                result.add(card);
            }
        }
        return result;
    }

    /**
     * Get all keycards by access level
     */
    public static List<Keycard> getKeycardsByAccessLevel(int accessLevel) {
        List<Keycard> result = new ArrayList<>();
        for (Keycard card : keycards) {
            if (card.getAccessLevel() == accessLevel) {
                result.add(card);
            }
        }
        return result;
    }

    /**
     * Deactivate a keycard
     */
    public static boolean deactivateKeycard(int keycardId) {
        Keycard card = getKeycardById(keycardId);
        if (card != null) {
            card.setActive(false);
            return true;
        }
        return false;
    }

    /**
     * Update access level
     */
    public static boolean updateAccessLevel(int keycardId, int newAccessLevel) {
        Keycard card = getKeycardById(keycardId);
        if (card != null && newAccessLevel >= 1 && newAccessLevel <= 4) {
            card.setAccessLevel(newAccessLevel);
            return true;
        }
        return false;
    }
}
