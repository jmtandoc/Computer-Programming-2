package com.motorph.ui;

import com.motorph.model.User;

import javax.swing.*;
import java.awt.*;

public class PayrollDashboardUI extends JFrame {
    public PayrollDashboardUI(User user) {
        setTitle("Payroll Dashboard");
        setSize(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome Payroll Officer: " + user.getUsername(), SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        // Add navigation buttons or panels here as needed
        setVisible(true);
    }
}
