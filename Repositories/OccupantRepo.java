package Repositories;

import Models.Occupant;
import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * OccupantRepo - Repository for Occupant database operations
 */
public class OccupantRepo {

    /**
     * Verify if an occupant exists with the given credentials
     * @param firstName First name of the occupant
     * @param lastName Last name of the occupant
     * @param occupantId ID of the occupant
     * @return true if occupant exists and credentials match, false otherwise
     */
    public static boolean verifyOccupant(String firstName, String lastName, int occupantId) {
        String sql = "SELECT COUNT(*) FROM occupants WHERE first_name = ? AND last_name = ? AND occupant_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, occupantId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error verifying occupant: " + e.getMessage());
        }

        return false;
    }

    /**
     * Get occupant by ID
     * @param occupantId ID of the occupant to retrieve
     * @return Occupant object if found, null otherwise
     */
    public static Occupant getOccupantById(int occupantId) {
        String sql = "SELECT occupant_id, first_name, last_name, email, phone, registered_at FROM occupants WHERE occupant_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, occupantId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Occupant(
                        rs.getInt("occupant_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("registered_at")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting occupant by ID: " + e.getMessage());
        }

        return null;
    }

    /**
     * Create a new occupant
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     * @param phone Phone number
     * @return true if created successfully, false otherwise
     */
    public static boolean createOccupant(String firstName, String lastName, String email, String phone) {
        String sql = "INSERT INTO occupants (first_name, last_name, email, phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error creating occupant: " + e.getMessage());
        }

        return false;
    }

    /**
     * Update occupant information
     * @param occupantId ID of the occupant to update
     * @param firstName New first name
     * @param lastName New last name
     * @param email New email
     * @param phone New phone
     * @return true if updated successfully, false otherwise
     */
    public static boolean updateOccupant(int occupantId, String firstName, String lastName, String email, String phone) {
        String sql = "UPDATE occupants SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE occupant_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setInt(5, occupantId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating occupant: " + e.getMessage());
        }

        return false;
    }

    /**
     * Get all occupants
     * @return List of all occupants
     */
    public static List<Occupant> getAllOccupants() {
        List<Occupant> occupants = new ArrayList<>();
        String sql = "SELECT occupant_id, first_name, last_name, email, phone, registered_at FROM occupants";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                occupants.add(new Occupant(
                    rs.getInt("occupant_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("registered_at")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all occupants: " + e.getMessage());
        }

        return occupants;
    }

    /**
     * Delete an occupant by ID
     * @param occupantId ID of the occupant to delete
     * @return true if deleted successfully, false otherwise
     */
    public static boolean deleteOccupant(int occupantId) {
        String sql = "DELETE FROM occupants WHERE occupant_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, occupantId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting occupant: " + e.getMessage());
        }

        return false;
    }
}
