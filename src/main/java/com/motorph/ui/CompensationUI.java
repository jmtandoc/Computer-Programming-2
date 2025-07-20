package com.motorph.ui;

import com.motorph.model.Employee;
import com.motorph.model.Payslip;
import com.motorph.model.AttendanceLog;
import com.motorph.database.Database;
import com.motorph.util.PayslipExporter;
import com.motorph.util.AttendanceExporter;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class CompensationUI extends JFrame {

    public CompensationUI(Employee emp) {
        setTitle("Compensation Summary");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel monthLabel = new JLabel("Filter by Month:");
        String[] months = {
            "January","February","March","April","May","June",
            "July","August","September","October","November","December"
        };
        JComboBox<String> monthDropdown = new JComboBox<>(months);
        JButton filterBtn = new JButton("Apply Filter");
        JButton exportPayslipBtn = new JButton("💾 Download Payslip");
        JButton exportAttendanceBtn = new JButton("📤 Download Attendance");

        topPanel.add(monthLabel);
        topPanel.add(monthDropdown);
        topPanel.add(filterBtn);
        topPanel.add(exportPayslipBtn);
        topPanel.add(exportAttendanceBtn);

        JTextArea resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        final List<Payslip>[] currentPayslips = new List[]{null};
        final List<AttendanceLog>[] currentAttendance = new List[]{null};

        filterBtn.addActionListener(e -> {
            String selectedMonth = (String) monthDropdown.getSelectedItem();
            resultArea.setText("");

            List<Payslip> payslips = Database.getPayslips().stream()
                .filter(p -> p.getEmpId().equals(emp.getEmpId()) &&
                             p.getPeriodStart().getMonth().name().equalsIgnoreCase(selectedMonth))
                .collect(Collectors.toList());

            currentPayslips[0] = payslips;

            List<AttendanceLog> logs = Database.getAttendanceLogs().stream()
                .filter(a -> a.getEmpId().equals(emp.getEmpId()) &&
                             a.getDate().contains(monthToMM(selectedMonth)))
                .collect(Collectors.toList());

            currentAttendance[0] = logs;

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            if (payslips.isEmpty()) {
                resultArea.append("❌ No payslips found for " + selectedMonth + ".\n\n");
            } else {
                for (Payslip p : payslips) {
                    resultArea.append("📄 Payslip for " + selectedMonth + "\n");
                    resultArea.append("Period: " + dtf.format(p.getPeriodStart()) + " to " + dtf.format(p.getPeriodEnd()) + "\n");
                    resultArea.append(String.format("Gross Pay: %.2f%n", p.getGrossPay()));
                    resultArea.append(String.format("Deductions: %.2f%n", p.getDeductions()));
                    resultArea.append(String.format("Net Pay: %.2f%n%n", p.getNetPay()));
                }
            }

            resultArea.append("🕒 Attendance Logs for " + selectedMonth + ":\n");
            for (AttendanceLog log : logs) {
                resultArea.append(log.getDate() + " → In: " + log.getTimeIn() + ", Out: " + log.getTimeOut() + "\n");
            }
        });

        exportPayslipBtn.addActionListener(e -> {
            if (currentPayslips[0] == null || currentPayslips[0].isEmpty()) {
                JOptionPane.showMessageDialog(this, "No payslip to export. Apply a filter first.");
            } else {
                for (Payslip p : currentPayslips[0]) {
                    PayslipExporter.exportPayslip(p);
                }
                JOptionPane.showMessageDialog(this, "Payslip saved to project folder!");
            }
        });

        exportAttendanceBtn.addActionListener(e -> {
            String selectedMonth = (String) monthDropdown.getSelectedItem();
            if (currentAttendance[0] == null || currentAttendance[0].isEmpty()) {
                JOptionPane.showMessageDialog(this, "No attendance logs to export.");
            } else {
                AttendanceExporter.exportAttendance(emp.getEmpId(), selectedMonth, currentAttendance[0]);
                JOptionPane.showMessageDialog(this, "Attendance saved to project folder.");
            }
        });

        setVisible(true);
    }

    private String monthToMM(String monthName) {
        return switch (monthName.toLowerCase()) {
            case "january" -> "01";
            case "february" -> "02";
            case "march" -> "03";
            case "april" -> "04";
            case "may" -> "05";
            case "june" -> "06";
            case "july" -> "07";
            case "august" -> "08";
            case "september" -> "09";
            case "october" -> "10";
            case "november" -> "11";
            case "december" -> "12";
            default -> "";
        };
    }
}
