package com.motorph.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AddEmployeeUI extends JFrame {
    private JTextField empIdField, nameField, positionField, supervisorField, deptField, roleField;
    private DefaultTableModel model;

    public AddEmployeeUI(DefaultTableModel model) {
        this.model = model;
        setTitle("Add Employee");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        empIdField = new JTextField();
        nameField = new JTextField();
        positionField = new JTextField();
        supervisorField = new JTextField();
        deptField = new JTextField();
        roleField = new JTextField();

        add(new JLabel("Emp ID:"));
        add(empIdField);
        add(new JLabel("Full Name:"));
        add(nameField);
        add(new JLabel("Position:"));
        add(positionField);
        add(new JLabel("Supervisor ID:"));
        add(supervisorField);
        add(new JLabel("Department:"));
        add(deptField);
        add(new JLabel("Role:"));
        add(roleField);

        JButton submitBtn = new JButton("Add");
        submitBtn.addActionListener(e -> {
            model.addRow(new Object[]{
                empIdField.getText(),
                nameField.getText(),
                positionField.getText(),
                supervisorField.getText(),
                deptField.getText(),
                roleField.getText().toUpperCase()
            });
            dispose();
        });

        add(new JLabel());
        add(submitBtn);
        setVisible(true);
    }
}
