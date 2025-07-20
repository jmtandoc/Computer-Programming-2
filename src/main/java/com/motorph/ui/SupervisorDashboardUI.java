package com.motorph.ui;

import com.motorph.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SupervisorDashboardUI extends JFrame {
    public SupervisorDashboardUI(User currentUser) {
        setTitle("Supervisor Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome, Supervisor " + currentUser.getUsername(), SwingConstants.CENTER);
        panel.add(welcomeLabel);

        JButton approveLeaveBtn = new JButton("Approve Leave Requests");
        JButton viewTeamBtn = new JButton("View Team Members");

        approveLeaveBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(null, "Approve Leave feature coming soon!"));

        viewTeamBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(null, "View Team feature coming soon!"));

        panel.add(approveLeaveBtn);
        panel.add(viewTeamBtn);

        add(panel);
        setVisible(true);
    }
}
