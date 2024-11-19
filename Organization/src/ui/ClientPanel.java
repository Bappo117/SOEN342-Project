package ui;

import dao.AuthenticationDAO;
import dao.BookingDAO;
import dao.GuardianDAO;
import dao.OfferingDAO;

import javax.swing.*;

public class ClientPanel {
    private JFrame frame;

    public void display() {
        frame = new JFrame("Client Panel");
        frame.setSize(400, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // DAO instances
        AuthenticationDAO authDAO = new AuthenticationDAO();
        BookingDAO bookingDAO = new BookingDAO();
        OfferingDAO offeringDAO = new OfferingDAO();
        GuardianDAO guardianDAO = new GuardianDAO();

        // Buttons
        JButton viewBookingsButton = new JButton("View Bookings");
        viewBookingsButton.setBounds(100, 50, 200, 25);
        panel.add(viewBookingsButton);

        JButton makeBookingButton = new JButton("Make Booking");
        makeBookingButton.setBounds(100, 100, 200, 25);
        panel.add(makeBookingButton);

        JButton viewOfferingsButton = new JButton("View Offerings");
        viewOfferingsButton.setBounds(100, 150, 200, 25);
        panel.add(viewOfferingsButton);

        JButton registerGuardianButton = new JButton("Register Guardian");
        registerGuardianButton.setBounds(100, 200, 200, 25);
        panel.add(registerGuardianButton);

        JButton logOutButton = new JButton("Log Out");
        logOutButton.setBounds(100, 250, 200, 25);
        panel.add(logOutButton);

        // View Bookings Functionality
        viewBookingsButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog("Enter your Username:");

            if (username == null || username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username is required to view bookings.");
                return;
            }

            int clientId = authDAO.getClientIdByUsername(username);

            if (clientId == -1) {
                JOptionPane.showMessageDialog(null, "Invalid username. Please try again.");
                return;
            }

            String bookings = bookingDAO.viewClientBookings(clientId);

            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No bookings found for this client.");
            } else {
                JOptionPane.showMessageDialog(null, "Your Bookings:\n" + bookings);
            }
        });

        // Make Booking Functionality
        makeBookingButton.addActionListener(e -> {
            String offeringIdInput = JOptionPane.showInputDialog("Enter Offering ID:");
            String username = JOptionPane.showInputDialog("Enter your Username:");

            if (offeringIdInput == null || username == null || offeringIdInput.isEmpty() || username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Offering ID and Username are required.");
                return;
            }

            try {
                int offeringId = Integer.parseInt(offeringIdInput.trim());
                int clientId = authDAO.getClientIdByUsername(username);

                if (clientId == -1) {
                    JOptionPane.showMessageDialog(null, "Invalid username. Please try again.");
                    return;
                }

                if (bookingDAO.makeBooking(offeringId, clientId)) {
                    JOptionPane.showMessageDialog(null, "Booking made successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Booking failed. The offering may not exist or be unavailable.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values for IDs.");
            }
        });

        // View Offerings Functionality
        viewOfferingsButton.addActionListener(e -> {
            String offerings = offeringDAO.viewOfferings();
            if (offerings.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No offerings available.");
            } else {
                JOptionPane.showMessageDialog(null, "Available Offerings:\n" + offerings);
            }
        });

        // Register Guardian for Underage Clients
        registerGuardianButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog("Enter your Username:");
            String ageInput = JOptionPane.showInputDialog("Enter your Age:");

            if (username == null || ageInput == null || username.isEmpty() || ageInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username and Age are required.");
                return;
            }

            try {
                int age = Integer.parseInt(ageInput.trim());
                if (age >= 18) {
                    JOptionPane.showMessageDialog(null, "You are not underage. No guardian is required.");
                    return;
                }

                int clientId = authDAO.getClientIdByUsername(username);
                if (clientId == -1) {
                    JOptionPane.showMessageDialog(null, "Invalid username. Please try again.");
                    return;
                }

                String guardianName = JOptionPane.showInputDialog("Enter Guardian's Name:");
                String guardianContact = JOptionPane.showInputDialog("Enter Guardian's Contact Information:");
                String relationship = JOptionPane.showInputDialog("Enter Relationship to Guardian:");

                if (guardianName == null || guardianContact == null || relationship == null ||
                        guardianName.isEmpty() || guardianContact.isEmpty() || relationship.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All guardian details are required.");
                    return;
                }

                if (guardianDAO.registerGuardian(clientId, guardianName, guardianContact, relationship)) {
                    JOptionPane.showMessageDialog(null, "Guardian registered successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to register guardian. Please try again.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input for age. Please enter a numeric value.");
            }
        });

        // Log Out Functionality
        logOutButton.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(() -> new LoginSignupUI().display());
        });
    }
}
