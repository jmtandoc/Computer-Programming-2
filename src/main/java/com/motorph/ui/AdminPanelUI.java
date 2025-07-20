package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.model.Employee;
import com.motorph.model.User;
import com.motorph.model.Role;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPanelUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public AdminPanelUI() {
        setTitle("Manage Employee Records");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 🔍 Table setup
        model = new DefaultTableModel(new Object[]{"Emp ID", "Name", "Position", "Department", "Username"}, 0);
        table = new JTable(model);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🚀 Button panel
        JPanel btnPanel = new JPanel(new FlowLayout());

        JButton addBtn = new JButton("➕ Add");
        JButton editBtn = new JButton("✏️ Edit");
        JButton deleteBtn = new JButton("❌ Delete");

        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        add(btnPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> openForm(null));
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String empId = (String) model.getValueAt(row, 0);
                Employee emp = Database.getEmployees().stream()
                        .filter(x -> x.getEmpId().equals(empId))
                        .findFirst().orElse(null);
                openForm(emp);
            }
        });
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String empId = (String) model.getValueAt(row, 0);
                Database.getEmployees().removeIf(x -> x.getEmpId().equals(empId));
                model.removeRow(row);
                JOptionPane.showMessageDialog(this, "✅ Record deleted.");
            }
        });

        setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (Employee emp : Database.getEmployees()) {
            model.addRow(new Object[]{
                    emp.getEmpId(),
                    emp.getFullName(),
                    emp.getPosition(),
                    emp.getDepartment(),
                    emp.getUserAccount().getUsername()
            });
        }
    }

    private void openForm(Employee existing) {
        JDialog dialog = new JDialog(this, "Employee Form", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new GridLayout(7, 2));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField deptField = new JTextField();
        JTextField posField = new JTextField();
        JTextField userField = new JTextField();
        JTextField passField = new JTextField();

        if (existing != null) {
            idField.setText(existing.getEmpId());
            nameField.setText(existing.getFullName());
            deptField.setText(existing.getDepartment());
            posField.setText(existing.getPosition());
            userField.setText(existing.getUserAccount().getUsername());
            passField.setText(existing.getUserAccount().getPassword());
        }

        dialog.add(new JLabel("Emp ID:")); dialog.add(idField);
        dialog.add(new JLabel("Full Name:")); dialog.add(nameField);
        dialog.add(new JLabel("Department:")); dialog.add(deptField);
        dialog.add(new JLabel("Position:")); dialog.add(posField);
        dialog.add(new JLabel("Username:")); dialog.add(userField);
        dialog.add(new JLabel("Password:")); dialog.add(passField);

        JButton saveBtn = new JButton("💾 Save");
        dialog.add(new JLabel());
        dialog.add(saveBtn);

        saveBtn.addActionListener(ev -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String dept = deptField.getText().trim();
            String pos = posField.getText().trim();
            String username = userField.getText().trim();
            String password = passField.getText().trim();

            Role role = Role.EMPLOYEE;
            if (pos.toLowerCase().contains("admin")) role = Role.ADMIN;
            else if (pos.toLowerCase().contains("supervisor") || pos.toLowerCase().contains("manager")) role = Role.SUPERVISOR;
            else if (pos.toLowerCase().contains("payroll")) role = Role.PAYROLL;

            User user = new User(username, password, true, role);
            Employee emp = new Employee(id, name, pos, "", user, dept, role);

            if (existing != null) {
                Database.getEmployees().removeIf(x -> x.getEmpId().equals(id));
            }
            Database.getEmployees().add(emp);
            refreshTable();
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "✅ Saved successfully.");
        });

        dialog.setVisible(true);
    }
}
