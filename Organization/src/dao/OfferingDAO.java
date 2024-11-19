package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferingDAO {

    private boolean isOfferingDuplicate(int locationId, String timeSlot, String dateRange) {
        String sql = "SELECT offering_id FROM Offering WHERE location_id = ? AND time_slot = ? AND date_range = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, locationId);
            stmt.setString(2, timeSlot);
            stmt.setString(3, dateRange);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean addOffering(int lessonId, int locationId, String timeSlot, String dateRange) {
        if (isOfferingDuplicate(locationId, timeSlot, dateRange)) {
            System.out.println("Error: An offering already exists for this location, time slot, and date range.");
            return false;
        }

        String sql = "INSERT INTO Offering (lesson_id, location_id, time_slot, date_range, is_available) " +
                "VALUES (?, ?, ?, ?, TRUE)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            stmt.setInt(2, locationId);
            stmt.setString(3, timeSlot);
            stmt.setString(4, dateRange);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





    public String viewOfferings() {
        StringBuilder offerings = new StringBuilder();
        String sql = "SELECT o.offering_id, l.lesson_name, loc.location_name, o.time_slot, o.date_range " +
                "FROM Offering o " +
                "JOIN Lesson l ON o.lesson_id = l.lesson_id " +
                "JOIN Location loc ON o.location_id = loc.location_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                offerings.append("Offering ID: ").append(rs.getInt("offering_id"))
                        .append(", Lesson: ").append(rs.getString("lesson_name"))
                        .append(", Location: ").append(rs.getString("location_name"))
                        .append(", Time Slot: ").append(rs.getString("time_slot"))
                        .append(", Date Range: ").append(rs.getString("date_range"))
                        .append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offerings.toString();
    }

    public boolean assignInstructorToOffering(int offeringId, int instructorId) {
        // Check if the instructor exists
        String checkInstructorQuery = "SELECT instructor_id FROM Instructor WHERE instructor_id = ?";
        String updateQuery = "UPDATE Offering SET instructor_id = ? WHERE offering_id = ? AND instructor_id IS NULL";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkInstructorQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            // Check if the instructor exists
            checkStmt.setInt(1, instructorId);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Instructor with ID " + instructorId + " does not exist.");
                return false;
            }

            // Assign instructor to offering
            updateStmt.setInt(1, instructorId);
            updateStmt.setInt(2, offeringId);
            int rowsUpdated = updateStmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String viewAllOfferingsForInstructors() {
        StringBuilder offerings = new StringBuilder();
        String query = "SELECT o.offering_id, o.lesson_id, o.time_slot, o.date_range, " +
                "COALESCE(i.name, 'Unassigned') AS instructor " +
                "FROM Offering o LEFT JOIN Instructor i ON o.instructor_id = i.instructor_id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                offerings.append("Offering ID: ").append(rs.getInt("offering_id"))
                        .append(", Lesson ID: ").append(rs.getInt("lesson_id"))
                        .append(", Time Slot: ").append(rs.getString("time_slot"))
                        .append(", Date Range: ").append(rs.getString("date_range"))
                        .append(", Instructor: ").append(rs.getString("instructor"))
                        .append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return offerings.toString();
    }
}
