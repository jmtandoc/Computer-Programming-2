package com.motorph.ui;

import com.motorph.service.AuthService;
import com.motorph.model.User;

import javax.swing.*;
import java.awt.*;

public class PasswordResetUI extends JFrame {
    private JPasswordField newPassField, confirmField;
    private User currentUser;

    public PasswordResetUI(User user) {
        this.currentUser = user;
        setTitle("Set New Password");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        newPassField = new JPasswordField();
        confirmField = new JPasswordField();

        add(new JLabel("New Password:"));
        add(newPassField);
        add(new JLabel("Confirm Password:"));
        add(confirmField);

        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> {
            String newPass = new String(newPassField.getPassword()).trim();
            String confirm = new String(confirmField.getPassword()).trim();

            if (newPass.isEmpty() || !newPass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match or are empty.");
            } else {
                AuthService.updatePassword(currentUser, newPass);
                JOptionPane.showMessageDialog(this, "Password updated successfully.");
                dispose();
                new LandingPageUI(currentUser); // Redirect to Landing Page
            }
        });

        add(new JLabel());
        add(resetBtn);
        setVisible(true);
    }
}
