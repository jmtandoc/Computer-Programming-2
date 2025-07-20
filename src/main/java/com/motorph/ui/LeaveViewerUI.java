package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.model.LeaveRequest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class LeaveViewerUI extends JFrame {
    public LeaveViewerUI() {
        setTitle("Leave Request Viewer");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel(new String[]{
            "Leave ID", "Employee ID", "Type", "Start", "End", "Status"
        }, 0);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        List<LeaveRequest> requests = Database.getLeaveRequests(); // This should be implemented in your Database class

        for (LeaveRequest r : requests) {
            model.addRow(new Object[]{
                r.getLeaveId(),
                r.getEmpId(),
                r.getLeaveType(),
                r.getStartDate(),
                r.getEndDate(),
                r.getStatus()
            });
        }

        add(new JLabel("All Leave Requests", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
