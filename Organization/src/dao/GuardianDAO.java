package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GuardianDAO {
    public boolean registerGuardian(int clientId, String guardianName, String contactInfo, String relationship) {
        String sql = "INSERT INTO Guardian (client_id, guardian_name, contact_info, relationship_to_client) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            stmt.setString(2, guardianName);
            stmt.setString(3, contactInfo);
            stmt.setString(4, relationship);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
