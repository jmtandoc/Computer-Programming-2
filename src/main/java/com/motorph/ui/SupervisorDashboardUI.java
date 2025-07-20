package com.motorph.ui;

import com.motorph.model.Employee;

import javax.swing.*;
import java.awt.*;

public class SupervisorDashboardUI extends JFrame {

    public SupervisorDashboardUI(Employee emp) {
        setTitle("Supervisor Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Supervisor " + emp.getFullName(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton approveLeaveBtn = new JButton("📋 Approve Leaves");
        JButton viewTeamBtn = new JButton("👥 View Team");

        approveLeaveBtn.addActionListener(e -> new LeaveApprovalUI(emp));
        viewTeamBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Feature coming soon!");
        });

        centerPanel.add(approveLeaveBtn);
        centerPanel.add(viewTeamBtn);

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
