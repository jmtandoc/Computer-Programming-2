package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.model.Employee;
import com.motorph.model.LeaveRequest;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

public class LeaveRequestUI extends JFrame {

    public LeaveRequestUI(Employee emp) {
        setTitle("File Leave Request");
        setSize(420, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        // 📋 Leave Type
        add(new JLabel("Leave Type:"));
        String[] leaveTypes = {"Sick", "Vacation", "Emergency", "Personal"};
        JComboBox<String> typeDropdown = new JComboBox<>(leaveTypes);
        add(typeDropdown);

        // 📅 Start Date Calendar Picker
        add(new JLabel("Start Date:"));
        SpinnerDateModel startModel = new SpinnerDateModel();
        JSpinner startSpinner = new JSpinner(startModel);
        startSpinner.setEditor(new JSpinner.DateEditor(startSpinner, "MM-dd-yyyy"));
        add(startSpinner);

        // 📅 End Date Calendar Picker
        add(new JLabel("End Date:"));
        SpinnerDateModel endModel = new SpinnerDateModel();
        JSpinner endSpinner = new JSpinner(endModel);
        endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, "MM-dd-yyyy"));
        add(endSpinner);

        // 📝 Reason field
        add(new JLabel("Reason:"));
        JTextField reasonField = new JTextField();
        add(reasonField);

        JButton submitBtn = new JButton("Submit");
        add(new JLabel()); // spacer
        add(submitBtn);

        submitBtn.addActionListener(e -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

                Date startDateRaw = (Date) startSpinner.getValue();
                Date endDateRaw = (Date) endSpinner.getValue();

                LocalDate startDate = startDateRaw.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endDate = endDateRaw.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (endDate.isBefore(startDate)) {
                    JOptionPane.showMessageDialog(this, "⚠️ End date cannot be before Start date.");
                    return;
                }

                String leaveId = "LR-" + UUID.randomUUID().toString().substring(0, 8);
                String type = (String) typeDropdown.getSelectedItem();
                String reason = reasonField.getText().trim();

                LeaveRequest lr = new LeaveRequest(leaveId, emp.getEmpId(), type, startDate, endDate, reason);
                Database.addLeaveRequest(lr);

                JOptionPane.showMessageDialog(this, "✅ Leave request submitted!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Something went wrong. Please double-check your entries.");
            }
        });

        setVisible(true);
    }
}
