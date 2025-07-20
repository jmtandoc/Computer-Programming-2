package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.model.Employee;
import com.motorph.model.LeaveRequest;
import com.motorph.model.Role;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class LeaveApprovalUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private Employee approver;

    public LeaveApprovalUI(Employee emp) {
        this.approver = emp;

        setTitle("Approve Leave Requests");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 📋 Table setup
        model = new DefaultTableModel(new Object[]{"Leave ID", "Employee ID", "Type", "Start", "End", "Reason", "Status"}, 0);
        table = new JTable(model);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ✅ Action Buttons
        JPanel actionPanel = new JPanel(new FlowLayout());

        JButton approveBtn = new JButton("✅ Approve");
        JButton rejectBtn = new JButton("❌ Reject");

        approveBtn.addActionListener(e -> updateLeaveStatus("Approved"));
        rejectBtn.addActionListener(e -> updateLeaveStatus("Rejected"));

        actionPanel.add(approveBtn);
        actionPanel.add(rejectBtn);

        add(actionPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        List<LeaveRequest> visibleRequests = Database.getLeaveRequests().stream()
            .filter(lr -> lr.getStatus().equalsIgnoreCase("Pending"))
            .filter(this::canApprove)
            .collect(Collectors.toList());

        for (LeaveRequest lr : visibleRequests) {
            model.addRow(new Object[]{
                    lr.getLeaveId(),
                    lr.getEmpId(),
                    lr.getLeaveType(),
                    lr.getStartDate().format(fmt),
                    lr.getEndDate().format(fmt),
                    lr.getReason(),
                    lr.getStatus()
            });
        }
    }

    private void updateLeaveStatus(String newStatus) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String leaveId = (String) model.getValueAt(row, 0);
            LeaveRequest lr = Database.findLeaveById(leaveId);

            if (lr != null && canApprove(lr)) {
                if (newStatus.equals("Approved")) lr.approve();
                else lr.reject();

                JOptionPane.showMessageDialog(this, "✅ Leave " + newStatus.toLowerCase() + ".");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ You are not authorized to update this request.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Please select a leave request first.");
        }
    }

    private boolean canApprove(LeaveRequest lr) {
        if (approver.getRole() == Role.ADMIN) return true;
        if (approver.getDepartment().equalsIgnoreCase("HR")) return true;

        if (approver.getRole() == Role.SUPERVISOR) {
            Employee requestor = Database.getEmployees().stream()
                    .filter(emp -> emp.getEmpId().equals(lr.getEmpId()))
                    .findFirst().orElse(null);

            if (requestor != null && requestor.getSupervisorId().equals(approver.getEmpId())) {
                return true;
            }
        }

        return false;
    }
}
