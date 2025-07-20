package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginUI() {
        setTitle("MotorPH Login");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            List<User> users = Database.getUsers();
            for (User user : users) {
                if (user.getUsername().equalsIgnoreCase(username)
                        && user.getPassword().equals(password)) {

                    Role role = user.getRole();
                    user.setFirstTimeLogin(false); // optional update

                    dispose(); // close login window

                    // 🚪 All roles launch same Landing Page
                    new LandingPageUI(user);
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "❌ Invalid username or password.");
        });

        add(userLabel); add(usernameField);
        add(passLabel); add(passwordField);
        add(new JLabel()); // empty spacer
        add(loginBtn);

        setVisible(true);
    }
}
