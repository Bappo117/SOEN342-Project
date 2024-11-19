package ui;

import dao.AuthenticationDAO;
import dao.GuardianDAO;

import javax.swing.*;

public class LoginSignupUI {
    private JFrame frame;

    public void display() {
        frame = new JFrame("Lesson Booking System - Login/Signup");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        
        AuthenticationDAO authDAO = new AuthenticationDAO();
        GuardianDAO guardianDAO = new GuardianDAO();

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(140, 50, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 90, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(140, 90, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 140, 120, 25);
        panel.add(loginButton);

        JButton signupButton = new JButton("Signup");
        signupButton.setBounds(180, 140, 120, 25);
        panel.add(signupButton);

      
        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passwordText.getPassword());

            if (authDAO.login(username, password)) {
                String role = authDAO.getRole(username);
                JOptionPane.showMessageDialog(null, "Login successful! Welcome, " + role);
                frame.dispose();
                switch (role) {
                    case "Administrator" -> new AdminPanel().display();
                    case "Client" -> new ClientPanel().display();
                    case "Instructor" -> new InstructorPanel().display();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
            }
        });

       
        signupButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog("Enter your Username:");
            String password = JOptionPane.showInputDialog("Enter your Password:");
            String[] roles = {"Administrator", "Client", "Instructor"};
            String role = (String) JOptionPane.showInputDialog(null, "Select Role:", "Signup",
                    JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

            if (role.equals("Client")) {
                String ageInput = JOptionPane.showInputDialog("Enter your Age:");

                try {
                    int age = Integer.parseInt(ageInput);
                    if (age < 18) {
                        JOptionPane.showMessageDialog(null, "You are underage. A guardian must be registered.");

                      
                        String guardianName = JOptionPane.showInputDialog("Enter Guardian's Name:");
                        String guardianContact = JOptionPane.showInputDialog("Enter Guardian's Contact Information:");
                        String relationship = JOptionPane.showInputDialog("Enter Relationship to Guardian:");

                        if (guardianName == null || guardianContact == null || relationship == null ||
                                guardianName.isEmpty() || guardianContact.isEmpty() || relationship.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Guardian registration failed. All details are required.");
                            return;
                        }

                        
                        if (authDAO.signup(username, password, role)) {
                            int clientId = authDAO.getClientIdByUsername(username);
                            if (clientId != -1 && guardianDAO.registerGuardian(clientId, guardianName, guardianContact, relationship)) {
                                JOptionPane.showMessageDialog(null, "Signup successful! Guardian registered.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Signup failed. Guardian registration unsuccessful.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Signup failed. Username may already exist.");
                        }
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid age input. Please enter a numeric value.");
                    return;
                }
            }

      
            if (authDAO.signup(username, password, role)) {
                JOptionPane.showMessageDialog(null, "Signup successful! Please login.");
            } else {
                JOptionPane.showMessageDialog(null, "Signup failed. Username may already exist.");
            }
        });
    }
}
