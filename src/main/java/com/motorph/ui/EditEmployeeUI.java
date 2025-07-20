package com.motorph.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EditEmployeeUI extends JFrame {
    private JTextField empIdField, nameField, positionField, supervisorField, deptField, roleField;
    private DefaultTableModel model;
    private int rowIndex;

    public EditEmployeeUI(int rowIndex, DefaultTableModel model) {
        this.rowIndex = rowIndex;
        this.model = model;

        setTitle("Edit Employee");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2));

        empIdField = new JTextField(model.getValueAt(rowIndex, 0).toString());
        nameField = new JTextField(model.getValueAt(rowIndex, 1).toString());
        positionField = new JTextField(model.getValueAt(rowIndex, 2).toString());
        supervisorField = new JTextField(model.getValueAt(rowIndex, 3).toString());
        deptField = new JTextField(model.getValueAt(rowIndex, 4).toString());
        roleField = new JTextField(model.getValueAt(rowIndex, 5).toString());

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

        JButton updateBtn = new JButton("Update");
        updateBtn.addActionListener(e -> {
            model.setValueAt(empIdField.getText(), rowIndex, 0);
            model.setValueAt(nameField.getText(), rowIndex, 1);
            model.setValueAt(positionField.getText(), rowIndex, 2);
            model.setValueAt(supervisorField.getText(), rowIndex, 3);
            model.setValueAt(deptField.getText(), rowIndex, 4);
            model.setValueAt(roleField.getText().toUpperCase(), rowIndex, 5);
            dispose();
        });

        add(new JLabel());
        add(updateBtn);
        setVisible(true);
    }
}
