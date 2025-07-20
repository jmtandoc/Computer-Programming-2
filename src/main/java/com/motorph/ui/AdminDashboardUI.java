package com.motorph.ui;

import com.motorph.model.User;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardUI extends JFrame {
    private User currentUser;

    public AdminDashboardUI(User user) {
        this.currentUser = user;
        setTitle("Admin Dashboard");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JLabel welcomeLabel = new JLabel("Welcome Admin: " + user.getUsername(), SwingConstants.CENTER);
        JButton manageEmployeesBtn = new JButton("Manage Employees");
        JButton viewLeavesBtn = new JButton("View Leave Requests");

        manageEmployeesBtn.addActionListener(e -> new EmployeeManagerUI());
        viewLeavesBtn.addActionListener(e -> new LeaveViewerUI());

        add(welcomeLabel);
        add(manageEmployeesBtn);
        add(viewLeavesBtn);
        setVisible(true);
    }
}
