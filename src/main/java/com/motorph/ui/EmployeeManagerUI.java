package com.motorph.ui;

import com.motorph.model.Employee;
import com.motorph.database.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeManagerUI extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel model;

    public EmployeeManagerUI() {
        setTitle("Employee Manager");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{
            "Emp ID", "Full Name", "Position", "Supervisor", "Department", "Role"
        }, 0);

        employeeTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load data
        List<Employee> employees = Database.getEmployees();
        for (Employee e : employees) {
            model.addRow(new Object[]{
                e.getEmpId(),
                e.getFullName(),
                e.getPosition(),
                e.getSupervisorId(),
                e.getDepartment(),
                e.getRole().name()
            });
        }

        // Buttons
        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(e -> new AddEmployeeUI(model));
        editBtn.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();
            if (row != -1) new EditEmployeeUI(row, model);
            else JOptionPane.showMessageDialog(this, "Select a row to edit.");
        });
        deleteBtn.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();
            if (row != -1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Delete selected employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                }
            } else JOptionPane.showMessageDialog(this, "Select a row to delete.");
        });

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
