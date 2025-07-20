package com.motorph.ui;

import com.motorph.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeeDashboardUI extends JFrame {
    public EmployeeDashboardUI(User currentUser) {
        setTitle("Employee Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername(), SwingConstants.CENTER);
        panel.add(welcomeLabel);

        JButton profileBtn = new JButton("View Profile");
        profileBtn.addActionListener(e -> JOptionPane.showMessageDialog(null, "Feature coming soon!"));
        panel.add(profileBtn);

        add(panel);
        setVisible(true);
    }
}
