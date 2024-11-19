package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.OfferingDAO;

public class BookingDAO {

    /**
     * Makes a booking for a client.
     *
     * @param offeringId the ID of the offering to book
     * @param clientId   the ID of the client making the booking
     * @return true if the booking is successful, false otherwise
     */
    public boolean makeBooking(int offeringId, int clientId) {
        if (!isOfferingAvailable(offeringId)) {
            System.out.println("Error: Offering is not available or does not exist.");
            return false;
        }

        String sql = "INSERT INTO Booking (offering_id, client_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, offeringId);
            stmt.setInt(2, clientId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isOfferingAvailable(int offeringId) {
        String sql = "SELECT is_available FROM Offering WHERE offering_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, offeringId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getBoolean("is_available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Cancels a booking for a client.
     *
     * @param bookingId the ID of the booking to cancel
     * @return true if the cancellation is successful, false otherwise
     */
    public boolean cancelBooking(int bookingId) {
        String sql = "DELETE FROM Booking WHERE booking_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all available offerings for clients to view.
     *
     * @return a formatted string of available offerings
     */
    public String viewAvailableOfferingsForClients() {
        StringBuilder offerings = new StringBuilder();
        String sql = "SELECT o.offering_id, l.lesson_name, loc.location_name, o.time_slot, o.date_range " +
                "FROM Offering o " +
                "JOIN Lesson l ON o.lesson_id = l.lesson_id " +
                "JOIN Location loc ON o.location_id = loc.location_id " +
                "WHERE o.is_available = TRUE";
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

    /**
     * Checks if an offering is already booked by the client.
     *
     * @param offeringId the ID of the offering
     * @param clientId   the ID of the client
     * @return true if the client has already booked the offering, false otherwise
     */
    public boolean isAlreadyBooked(int offeringId, int clientId) {
        String sql = "SELECT * FROM Booking WHERE offering_id = ? AND client_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, offeringId);
            stmt.setInt(2, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String viewClientBookings(int clientId) {
        StringBuilder bookings = new StringBuilder();
        String sql = "SELECT b.booking_id, o.offering_id, l.lesson_name, loc.location_name, o.time_slot, o.date_range " +
                "FROM Booking b " +
                "JOIN Offering o ON b.offering_id = o.offering_id " +
                "JOIN Lesson l ON o.lesson_id = l.lesson_id " +
                "JOIN Location loc ON o.location_id = loc.location_id " +
                "WHERE b.client_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    bookings.append("Booking ID: ").append(rs.getInt("booking_id"))
                            .append(", Offering ID: ").append(rs.getInt("offering_id"))
                            .append(", Lesson: ").append(rs.getString("lesson_name"))
                            .append(", Location: ").append(rs.getString("location_name"))
                            .append(", Time Slot: ").append(rs.getString("time_slot"))
                            .append(", Date Range: ").append(rs.getString("date_range"))
                            .append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings.toString();
    }
}
