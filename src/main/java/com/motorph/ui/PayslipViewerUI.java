package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.model.Payslip;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PayslipViewerUI extends JFrame {
    public PayslipViewerUI() {
        setTitle("Payslip Viewer");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel(new String[]{
            "Payslip ID", "Employee ID", "Period", "Gross Pay", "Deductions", "Net Pay"
        }, 0);

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        List<Payslip> payslips = Database.getPayslips();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");

        for (Payslip p : payslips) {
            String period = formatter.format(p.getPeriodStart()) + " – " + formatter.format(p.getPeriodEnd());
            model.addRow(new Object[]{
                p.getPayslipId(),
                p.getEmpId(),
                period,
                String.format("%.2f", p.getGrossPay()),
                String.format("%.2f", p.getDeductions()),
                String.format("%.2f", p.getNetPay())
            });
        }

        add(new JLabel("Generated Payslips", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}
