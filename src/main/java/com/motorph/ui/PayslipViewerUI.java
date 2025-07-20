package com.motorph.ui;

import com.motorph.model.*;
import com.motorph.database.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PayslipViewerUI extends JFrame {

    public PayslipViewerUI(Employee emp) {
        setTitle("Payslip Viewer");
        setSize(760, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String monthName = "July";
        int targetMonth = 7;

        JLabel heading = new JLabel("📄 Payslip for " + monthName, SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        LocalDate start = LocalDate.of(2025, targetMonth, 1);
        LocalDate end = LocalDate.of(2025, targetMonth, 31);

        double gross = emp.getBasicSalary() + emp.getRiceSubsidy()
                     + emp.getPhoneAllowance() + emp.getClothingAllowance();
        double deductions = emp.getBasicSalary() * 0.045
                          + emp.getBasicSalary() * 0.035
                          + emp.getBasicSalary() * 0.02;
        double net = gross - deductions;

        Payslip slip = new Payslip(emp.getEmpId(), start, end, gross, deductions);

        JTextArea details = new JTextArea();
        details.setEditable(false);
        details.setFont(new Font("Monospaced", Font.PLAIN, 14));
        details.setText("""
                Period: %s to %s
                Gross Pay: %.2f
                Deductions: %.2f
                Net Pay: %.2f
                """.formatted(start.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                              end.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                              gross, deductions, net));

        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createTitledBorder("💼 Payroll Summary"));
        summaryPanel.add(details, BorderLayout.CENTER);

        JTable table = new JTable(getAttendanceModel(emp.getEmpId(), targetMonth));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("🕒 Attendance Logs for " + monthName));

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.add(summaryPanel);
        centerPanel.add(scroll);

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private DefaultTableModel getAttendanceModel(String empId, int monthFilter) {
        String[] columns = {"Date", "Time In", "Time Out"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        DateTimeFormatter[] formatters = {
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy")
        };

        for (AttendanceLog log : Database.getAttendanceLogs()) {
            if (!log.getEmpId().equals(empId)) continue;

            LocalDate parsedDate = null;
            for (DateTimeFormatter fmt : formatters) {
                try {
                    parsedDate = LocalDate.parse(log.getDate(), fmt);
                    break;
                } catch (DateTimeParseException ignored) {}
            }

            if (parsedDate != null && parsedDate.getMonthValue() == monthFilter) {
                model.addRow(new Object[] { log.getDate(), log.getTimeIn(), log.getTimeOut() });
            }
        }

        return model;
    }
}
