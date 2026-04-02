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
}
