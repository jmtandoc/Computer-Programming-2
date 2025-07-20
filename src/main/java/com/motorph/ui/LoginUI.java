package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.model.*;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    public LoginUI() {
        setTitle("MotorPH Login");
        setSize(420, 240);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel());
        JButton loginBtn = new JButton("🔓 Login");
        add(loginBtn);

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            User user = Database.findUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                Employee emp = Database.getEmployeeByUsername(username);
                Role role = user.getRole();

                switch (role) {
                    case ADMIN -> new LandingPageUI(user);
                    case SUPERVISOR -> new SupervisorDashboardUI(emp);
                    case PAYROLL -> JOptionPane.showMessageDialog(this, "Payroll dashboard coming soon!");
                    case EMPLOYEE -> new LandingPageUI(user);
                    default -> JOptionPane.showMessageDialog(this, "⚠️ Unknown role.");
                }

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid credentials.");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        Database.loadFromExcel("src/main/resources/MotorPH_Employee_Data.xlsx");
        new LoginUI();
    }
}
