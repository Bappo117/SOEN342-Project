package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorDAO {
    public boolean addInstructor(String name, String phoneNumber, String specialization, String citiesAvailable) {
        String query = "INSERT INTO Instructor (name, phone_number, specialization, cities_available) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, specialization);
            stmt.setString(4, citiesAvailable);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getInstructorIdByName(String name) {
        String query = "SELECT instructor_id FROM Instructor WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("instructor_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String listInstructors() {
        StringBuilder instructors = new StringBuilder();
        String query = "SELECT * FROM Instructor";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                instructors.append("ID: ").append(rs.getInt("instructor_id"))
                        .append(", Name: ").append(rs.getString("name"))
                        .append(", Phone: ").append(rs.getString("phone_number"))
                        .append(", Specialization: ").append(rs.getString("specialization"))
                        .append(", Cities Available: ").append(rs.getString("cities_available"))
                        .append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructors.toString();
    }

    public boolean instructorExists(int instructorId) {
        String query = "SELECT instructor_id FROM Instructor WHERE instructor_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, instructorId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
