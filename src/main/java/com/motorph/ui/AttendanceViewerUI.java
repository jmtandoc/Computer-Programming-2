package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.model.AttendanceLog;
import com.motorph.model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AttendanceViewerUI extends JFrame {

    public AttendanceViewerUI(Employee emp) {
        setTitle("Attendance History");
        setSize(500, 300);
        setLocationRelativeTo(null);

        String[] cols = {"Date", "Time In", "Time Out"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        List<AttendanceLog> logs = Database.getAttendanceLogs();
        for (AttendanceLog log : logs) {
            if (log.getEmpId().equalsIgnoreCase(emp.getEmpId())) {
                model.addRow(new String[]{log.getDate(), log.getTimeIn(), log.getTimeOut()});
            }
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
        setVisible(true);
    }
}
