package com.motorph.ui;

import com.motorph.model.User;

import javax.swing.*;
import java.awt.*;

public class PasswordChangeUI extends JFrame {

    public PasswordChangeUI(User user) {
        setTitle("Change Password");
        setSize(300, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        JTextField oldField = new JTextField();
        JTextField newField = new JTextField();
        JButton confirmBtn = new JButton("Update");

        confirmBtn.addActionListener(e -> {
            String oldPass = oldField.getText().trim();
            String newPass = newField.getText().trim();

            if (user.getPassword().equals(oldPass)) {
                user.setPassword(newPass);
                JOptionPane.showMessageDialog(this, "✅ Password updated successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Incorrect old password.");
            }
        });

        add(new JLabel("Old Password:")); add(oldField);
        add(new JLabel("New Password:")); add(newField);
        add(new JLabel()); add(confirmBtn);

        setVisible(true);
    }
}
