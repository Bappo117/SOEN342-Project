package ui;

import dao.InstructorDAO;
import dao.OfferingDAO;

import javax.swing.*;

public class InstructorPanel {
    private JFrame frame;

    public void display() {
        frame = new JFrame("Instructor Panel");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

      
        JButton registerButton = new JButton("Register as Instructor");
        registerButton.setBounds(150, 20, 200, 25);
        panel.add(registerButton);

        JButton listInstructorsButton = new JButton("List All Instructors");
        listInstructorsButton.setBounds(150, 60, 200, 25);
        panel.add(listInstructorsButton);

        JButton viewOfferingsButton = new JButton("View All Offerings");
        viewOfferingsButton.setBounds(150, 100, 200, 25);
        panel.add(viewOfferingsButton);

        JButton takeOfferingButton = new JButton("Take Offering");
        takeOfferingButton.setBounds(150, 140, 200, 25);
        panel.add(takeOfferingButton);

        JButton logOutButton = new JButton("Log Out");
        logOutButton.setBounds(150, 180, 200, 25);
        panel.add(logOutButton);

        
        InstructorDAO instructorDAO = new InstructorDAO();
        OfferingDAO offeringDAO = new OfferingDAO();

        
        registerButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter your Name:");
            String phone = JOptionPane.showInputDialog("Enter your Phone Number:");
            String specialization = JOptionPane.showInputDialog("Enter your Specialization:");
            String citiesAvailable = JOptionPane.showInputDialog("Enter Cities Available to Work:");

            if (name == null || phone == null || specialization == null || citiesAvailable == null) {
                JOptionPane.showMessageDialog(null, "All fields are required.");
                return;
            }

            if (instructorDAO.addInstructor(name, phone, specialization, citiesAvailable)) {
                JOptionPane.showMessageDialog(null, "Instructor registration successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
            }
        });

        
        listInstructorsButton.addActionListener(e -> {
            String instructors = instructorDAO.listInstructors();
            JOptionPane.showMessageDialog(null, "List of Instructors:\n" + instructors);
        });

       
        viewOfferingsButton.addActionListener(e -> {
            String offerings = offeringDAO.viewOfferings();
            if (offerings.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No offerings available for instructors.");
            } else {
                JOptionPane.showMessageDialog(null, "Offerings:\n" + offerings);
            }
        });


       
        takeOfferingButton.addActionListener(e -> {
            String offeringIdInput = JOptionPane.showInputDialog("Enter Offering ID:");
            String instructorIdInput = JOptionPane.showInputDialog("Enter Your Instructor ID:");

            if (offeringIdInput == null || instructorIdInput == null) {
                JOptionPane.showMessageDialog(null, "Offering ID and Instructor ID are required.");
                return;
            }

            try {
                int offeringId = Integer.parseInt(offeringIdInput.trim());
                int instructorId = Integer.parseInt(instructorIdInput.trim());

                if (!instructorDAO.instructorExists(instructorId)) {
                    JOptionPane.showMessageDialog(null, "Invalid Instructor ID. Please register first.");
                    return;
                }

                if (offeringDAO.assignInstructorToOffering(offeringId, instructorId)) {
                    JOptionPane.showMessageDialog(null, "You have been successfully assigned to the offering!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to take the offering. "
                            + "Ensure the offering exists, and it hasn't already been assigned.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values for IDs.");
            }
        });

      
        logOutButton.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(() -> new LoginSignupUI().display());
        });
    }
}
