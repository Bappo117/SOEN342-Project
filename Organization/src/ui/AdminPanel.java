package ui;

import dao.LessonDAO;
import dao.LocationDAO;
import dao.OfferingDAO;

import javax.swing.*;

public class AdminPanel {
    private JFrame frame;

    public void display() {
        frame = new JFrame("Administrator Panel");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton createLessonButton = new JButton("Create Lesson");
        createLessonButton.setBounds(150, 50, 200, 25);
        panel.add(createLessonButton);

        JButton createLocationButton = new JButton("Create Location");
        createLocationButton.setBounds(150, 90, 200, 25);
        panel.add(createLocationButton);

        JButton createOfferingButton = new JButton("Create Offering");
        createOfferingButton.setBounds(150, 130, 200, 25);
        panel.add(createOfferingButton);

        JButton viewOfferingsButton = new JButton("View Offerings");
        viewOfferingsButton.setBounds(150, 170, 200, 25);
        panel.add(viewOfferingsButton);

        JButton logOutButton = new JButton("Log Out");
        logOutButton.setBounds(150, 210, 200, 25);
        panel.add(logOutButton);

       
        LessonDAO lessonDAO = new LessonDAO();
        LocationDAO locationDAO = new LocationDAO();
        OfferingDAO offeringDAO = new OfferingDAO();

       
        createLessonButton.addActionListener(e -> {
            String lessonName = JOptionPane.showInputDialog("Enter Lesson Name:");
            String duration = JOptionPane.showInputDialog("Enter Duration (e.g., 1 hour):");

            if (lessonName == null || duration == null || lessonName.isEmpty() || duration.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Lesson Name and Duration are required.");
                return;
            }

            if (lessonDAO.createLesson(lessonName, duration)) {
                JOptionPane.showMessageDialog(null, "Lesson created successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Error creating lesson.");
            }
        });

        
        createLocationButton.addActionListener(e -> {
            String locationName = JOptionPane.showInputDialog("Enter Location Name:");
            String address = JOptionPane.showInputDialog("Enter Address:");

            if (locationName == null || address == null || locationName.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Location Name and Address are required.");
                return;
            }

            if (locationDAO.createLocation(locationName, address)) {
                JOptionPane.showMessageDialog(null, "Location created successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Error creating location.");
            }
        });

       
        createOfferingButton.addActionListener(e -> {
            String lessonIdInput = JOptionPane.showInputDialog("Enter Lesson ID:");
            String locationIdInput = JOptionPane.showInputDialog("Enter Location ID:");
            String timeSlot = JOptionPane.showInputDialog("Enter Time Slot (e.g., 10:00-11:00):");
            String dateRange = JOptionPane.showInputDialog("Enter Date Range (e.g., 2024-01-01 to 2024-02-01):");

            if (lessonIdInput == null || locationIdInput == null || timeSlot == null || dateRange == null ||
                    lessonIdInput.isEmpty() || locationIdInput.isEmpty() || timeSlot.isEmpty() || dateRange.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required to create an offering.");
                return;
            }

            try {
                int lessonId = Integer.parseInt(lessonIdInput);
                int locationId = Integer.parseInt(locationIdInput);

                if (offeringDAO.addOffering(lessonId, locationId, timeSlot, dateRange)) {
                    JOptionPane.showMessageDialog(null, "Offering created successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Error creating offering. Please check the inputs.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Lesson ID and Location ID must be numeric values.");
            }
        });

       
        viewOfferingsButton.addActionListener(e -> {
            String offerings = offeringDAO.viewOfferings();
            if (offerings.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No offerings found.");
            } else {
                JOptionPane.showMessageDialog(null, "Offerings:\n" + offerings);
            }
        });


       
        logOutButton.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(() -> new LoginSignupUI().display());
        });
    }
}
