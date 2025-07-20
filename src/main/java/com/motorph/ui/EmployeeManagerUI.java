package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.model.Employee;
import com.motorph.model.Role;
import com.motorph.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeManagerUI extends JFrame {

    private DefaultTableModel model;
    private JTable table;

    public EmployeeManagerUI() {
        setTitle("Manage Employees");
        setSize(680, 380);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] cols = {"ID", "Name", "Department", "Role"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        refreshTable();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton addBtn = new JButton("➕ Add");
        JButton editBtn = new JButton("✏️ Edit");
        JButton deleteBtn = new JButton("🗑️ Delete");

        addBtn.addActionListener(e -> addEmployeeDialog());
        editBtn.addActionListener(e -> editEmployeeDialog());
        deleteBtn.addActionListener(e -> deleteSelectedEmployee());

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);

        add(btnPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Employee> employees = Database.getEmployees();
        for (Employee emp : employees) {
            model.addRow(new String[]{
                emp.getEmpId(),
                emp.getFullName(),
                emp.getDepartment(),
                emp.getRole().toString()
            });
        }
    }

    private void addEmployeeDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField deptField = new JTextField();
        JComboBox<Role> roleBox = new JComboBox<>(Role.values());
        JTextField userField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Employee ID:")); panel.add(idField);
        panel.add(new JLabel("Full Name:")); panel.add(nameField);
        panel.add(new JLabel("Department:")); panel.add(deptField);
        panel.add(new JLabel("Role:")); panel.add(roleBox);
        panel.add(new JLabel("Username:")); panel.add(userField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String empId = idField.getText().trim();
            String name = nameField.getText().trim();
            String dept = deptField.getText().trim();
            Role role = (Role) roleBox.getSelectedItem();
            String username = userField.getText().trim();

            User user = Database.findUserByUsername(username);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "❌ No user found with that username.");
                return;
            }

            Employee emp = new Employee(empId, name, "Position", "Supervisor", user, dept, role);
            Database.getEmployees().add(emp);
            refreshTable();
            JOptionPane.showMessageDialog(this, "✅ Employee added.");
        }
    }

    private void editEmployeeDialog() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "❌ Select a row to edit.");
            return;
        }

        String empId = model.getValueAt(row, 0).toString();
        Employee emp = Database.getEmployees().stream()
                .filter(e -> e.getEmpId().equalsIgnoreCase(empId))
                .findFirst().orElse(null);

        if (emp == null) return;

        JTextField nameField = new JTextField(emp.getFullName());
        JTextField deptField = new JTextField(emp.getDepartment());
        JComboBox<Role> roleBox = new JComboBox<>(Role.values());
        roleBox.setSelectedItem(emp.getRole());

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Full Name:")); panel.add(nameField);
        panel.add(new JLabel("Department:")); panel.add(deptField);
        panel.add(new JLabel("Role:")); panel.add(roleBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            emp.setFullName(nameField.getText().trim());
            emp.setDepartment(deptField.getText().trim());
            emp.setRole((Role) roleBox.getSelectedItem());
            refreshTable();
            JOptionPane.showMessageDialog(this, "✏️ Employee updated.");
        }
    }

    private void deleteSelectedEmployee() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "❌ Select a row to delete.");
            return;
        }

        String empId = model.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Delete employee " + empId + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Database.getEmployees().removeIf(e -> e.getEmpId().equalsIgnoreCase(empId));
            refreshTable();
            JOptionPane.showMessageDialog(this, "🗑️ Employee deleted.");
        }
    }
}
