package com.motorph.ui;

import com.motorph.model.Employee;

import javax.swing.*;
import java.awt.*;

public class EmployeeDashboardUI extends JFrame {

    public EmployeeDashboardUI(Employee emp) {
        setTitle("Welcome " + emp.getFullName());
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(0, 2));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Employee Summary"));

        infoPanel.add(new JLabel("Employee ID:"));
        infoPanel.add(new JLabel(emp.getEmpId()));

        infoPanel.add(new JLabel("Name:"));
        infoPanel.add(new JLabel(emp.getFullName()));

        infoPanel.add(new JLabel("Position:"));
        infoPanel.add(new JLabel(emp.getPosition()));

        infoPanel.add(new JLabel("Supervisor:"));
        infoPanel.add(new JLabel(emp.getSupervisorId()));

        infoPanel.add(new JLabel("Department:"));
        infoPanel.add(new JLabel(emp.getDepartment()));

        infoPanel.add(new JLabel("Phone:"));
        infoPanel.add(new JLabel(emp.getPhoneNumber()));

        infoPanel.add(new JLabel("SSS #:"));
        infoPanel.add(new JLabel(emp.getSssNumber()));

        add(infoPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
