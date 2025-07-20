package com.motorph.ui;

import com.motorph.model.User;
import com.motorph.model.Role;
import com.motorph.service.AuthService;
import com.motorph.database.Database;
import com.motorph.ui.AdminDashboardUI;
import com.motorph.ui.EmployeeDashboardUI;
import com.motorph.ui.SupervisorDashboardUI;
import com.motorph.ui.PayrollDashboardUI;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginUI() {
        setTitle("MotorPH Login");
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        add(new JLabel());
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                System.out.println("🔐 You typed: " + username + " / " + password);

                List<User> users = Database.getUsers();
                AuthService authService = new AuthService(users);
                User currentUser = authService.login(username, password);

                if (currentUser != null) {
                    Role role = currentUser.getRole();
                    switch (role) {
                        case ADMIN -> new AdminDashboardUI().setVisible(true);
                        case SUPERVISOR -> new SupervisorDashboardUI(currentUser).setVisible(true);
                        case EMPLOYEE -> new EmployeeDashboardUI(currentUser).setVisible(true);
                        case PAYROLL -> new PayrollDashboardUI().setVisible(true);
                        default -> JOptionPane.showMessageDialog(null, "Unknown role.");
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Try again.");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        Database.loadFromExcel("src/main/resources/MotorPH Employee Data.xlsx");
        new LoginUI();
    }
}
