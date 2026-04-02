package Repositories;

import Models.Keycard;
import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * KeycardRepo - Repository for Keycard database operations
 */
public class KeycardRepo {

    /**
     * Get all keycards for a specific occupant
     * @param occupantId ID of the occupant
     * @return List of Keycard objects for the occupant
     */
    public static List<Keycard> getKeycardsByOccupantId(int occupantId) {
        List<Keycard> keycards = new ArrayList<>();
        String sql = "SELECT keycard_id, occupant_id, keycard_code, is_active, issued_at FROM keycards WHERE occupant_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, occupantId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Keycard keycard = new Keycard(
                        rs.getInt("keycard_id"),
                        rs.getInt("occupant_id"),
                        rs.getString("keycard_code"),
                        rs.getBoolean("is_active"),
                        rs.getString("issued_at")
                    );
                    keycards.add(keycard);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting keycards by occupant ID: " + e.getMessage());
        }

        return keycards;
    }

    /**
     * Get keycard by ID
     * @param keycardId ID of the keycard
     * @return Keycard object if found, null otherwise
     */
    public static Keycard getKeycardById(int keycardId) {
        String sql = "SELECT keycard_id, occupant_id, keycard_code, is_active, issued_at FROM keycards WHERE keycard_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, keycardId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Keycard(
                        rs.getInt("keycard_id"),
                        rs.getInt("occupant_id"),
                        rs.getString("keycard_code"),
                        rs.getBoolean("is_active"),
                        rs.getString("issued_at")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting keycard by ID: " + e.getMessage());
        }

        return null;
    }

    /**
     * Create a new keycard
     * @param occupantId ID of the occupant
     * @param keycardCode Code for the keycard
     * @return true if created successfully, false otherwise
     */
    public static boolean createKeycard(int occupantId, String keycardCode) {
        String sql = "INSERT INTO keycards (occupant_id, keycard_code) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, occupantId);
            stmt.setString(2, keycardCode);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error creating keycard: " + e.getMessage());
        }

        return false;
    }

    /**
     * Update keycard active status
     * @param keycardId ID of the keycard
     * @param isActive New active status
     * @return true if updated successfully, false otherwise
     */
    public static boolean updateKeycardStatus(int keycardId, boolean isActive) {
        String sql = "UPDATE keycards SET is_active = ? WHERE keycard_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isActive);
            stmt.setInt(2, keycardId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating keycard status: " + e.getMessage());
        }

        return false;
    }

    /**
     * Get all keycards
     * @return List of all keycards
     */
    public static List<Keycard> getAllKeycards() {
        List<Keycard> keycards = new ArrayList<>();
        String sql = "SELECT keycard_id, occupant_id, keycard_code, is_active, issued_at FROM keycards";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                keycards.add(new Keycard(
                    rs.getInt("keycard_id"),
                    rs.getInt("occupant_id"),
                    rs.getString("keycard_code"),
                    rs.getBoolean("is_active"),
                    rs.getString("issued_at")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all keycards: " + e.getMessage());
        }

        return keycards;
    }

    /**
     * Delete a keycard by ID
     * @param keycardId ID of the keycard to delete
     * @return true if deleted successfully, false otherwise
     */
    public static boolean deleteKeycard(int keycardId) {
        String sql = "DELETE FROM keycards WHERE keycard_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, keycardId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting keycard: " + e.getMessage());
        }

        return false;
    }
}
