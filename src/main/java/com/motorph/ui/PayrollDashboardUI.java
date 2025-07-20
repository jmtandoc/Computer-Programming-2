package com.motorph.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PayrollDashboardUI extends JFrame {
    public PayrollDashboardUI() {
        setTitle("Payroll Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Welcome to Payroll Dashboard", SwingConstants.CENTER));

        JButton generatePayslipBtn = new JButton("Generate Payslip");
        JButton processPayrollBtn = new JButton("Process Payroll");

        generatePayslipBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(null, "Payslip generation coming soon!"));

        processPayrollBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(null, "Payroll processing coming soon!"));

        panel.add(generatePayslipBtn);
        panel.add(processPayrollBtn);

        add(panel);
        setVisible(true);
    }
}
