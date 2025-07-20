package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.model.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AdminDashboardUI extends JFrame {
    public AdminDashboardUI() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Welcome, Admin!", SwingConstants.CENTER));

        JButton manageUsers = new JButton("Manage Users");
        JButton viewEmployees = new JButton("View All Employees");

        panel.add(manageUsers);
        panel.add(viewEmployees);
        add(panel);

        // 🔗 Wire up "View All Employees" button
        viewEmployees.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Employee> employees = Database.getEmployees();
                new EmployeeListUI(employees).setVisible(true);
            }
        });

        // ✨ Optional: placeholder for "Manage Users"
        manageUsers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Manage Users feature coming soon!");
            }
        });

        setVisible(true);
    }
}
