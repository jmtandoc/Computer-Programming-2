package com.motorph.ui;

import com.motorph.model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeListUI extends JFrame {
    public EmployeeListUI(List<Employee> employees) {
        setTitle("All Employees");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] columns = {"Employee ID", "Full Name", "Department", "Role", "Username"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Employee emp : employees) {
            String empId = emp.getEmpId();
            String fullName = emp.getFullName();
            String department = emp.getDepartment();
            String role = emp.getRole().name();
            String username = emp.getUserAccount() != null ? emp.getUserAccount().getUsername() : "—";

            model.addRow(new Object[]{empId, fullName, department, role, username});
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);  // ✅ Make sure this line is here
    }
}
