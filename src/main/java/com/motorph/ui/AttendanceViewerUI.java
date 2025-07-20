package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.model.AttendanceLog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class AttendanceViewerUI extends JFrame {
    public AttendanceViewerUI() {
        setTitle("Attendance Record");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel(new String[]{
            "Employee ID", "Date", "Time In", "Time Out"
        }, 0);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        List<AttendanceLog> logs = Database.getAttendanceLogs(); // Load from Excel or hardcoded test entries

        for (AttendanceLog log : logs) {
            model.addRow(new Object[]{
                log.getEmpId(),
                log.getDate(),
                log.getTimeIn(),
                log.getTimeOut()
            });
        }

        add(new JLabel("Employee Attendance", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
