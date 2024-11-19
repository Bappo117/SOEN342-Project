package dao;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthenticationDAO {
    public boolean login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false; // Prevent empty input
        }
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean signup(String username, String password, String role) {
        String userInsertQuery = "INSERT INTO User (username, password, role) VALUES (?, ?, ?)";
        String clientInsertQuery = "INSERT INTO Client (name, age, guardian_id, user_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userInsertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement clientStmt = conn.prepareStatement(clientInsertQuery)) {

            // Insert into User table
            userStmt.setString(1, username);
            userStmt.setString(2, password);
            userStmt.setString(3, role);
            userStmt.executeUpdate();

            // Get the generated user_id
            ResultSet rs = userStmt.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);

                // If the role is 'Client', also insert into the Client table
                if ("Client".equalsIgnoreCase(role)) {
                    String clientName = JOptionPane.showInputDialog("Enter Client Name:");
                    String ageInput = JOptionPane.showInputDialog("Enter Client Age:");
                    int age = Integer.parseInt(ageInput);

                    // Optional: Ask for guardian if underage
                    Integer guardianId = null;
                    if (age < 18) {
                        String guardianInput = JOptionPane.showInputDialog("Enter Guardian ID (if any):");
                        if (guardianInput != null && !guardianInput.trim().isEmpty()) {
                            guardianId = Integer.parseInt(guardianInput.trim());
                        }
                    }

                    // Insert into Client table
                    clientStmt.setString(1, clientName);
                    clientStmt.setInt(2, age);
                    if (guardianId != null) {
                        clientStmt.setInt(3, guardianId);
                    } else {
                        clientStmt.setNull(3, java.sql.Types.INTEGER);
                    }
                    clientStmt.setInt(4, userId);
                    clientStmt.executeUpdate();
                }
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getRole(String username) {
        if (username == null || username.isEmpty()) {
            return null; // Prevent invalid input
        }
        String query = "SELECT role FROM User WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getClientIdByUsername(String username) {
        String query = "SELECT client_id FROM Client c INNER JOIN User u ON c.user_id = u.user_id WHERE u.username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("client_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if client_id not found
    }

}
