/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystemgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 *
 * @author jhoan
 */
public class Payslip extends JFrame {
    private User user;

    private JTextArea txtPayslip;
    private JButton btnExportPDF;

    public Payslip(User user) {
        this.user = user;

        setTitle("Payslip");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtPayslip = new JTextArea();
        txtPayslip.setEditable(false);
        add(new JScrollPane(txtPayslip), BorderLayout.CENTER);

        btnExportPDF = new JButton("Export to PDF");
        add(btnExportPDF, BorderLayout.SOUTH);

        generatePayslip();

        btnExportPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // For now, just show a message. PDF export can be implemented with a library like iText.
                JOptionPane.showMessageDialog(Payslip.this, "Export to PDF feature coming soon.");
            }
        });
    }

    private void generatePayslip() {
        StringBuilder sb = new StringBuilder();
        sb.append("Payslip for ").append(user.getFullName()).append("\n");
        sb.append("Date: ").append(LocalDate.now()).append("\n");
        sb.append("---------------------------------------\n");

        // Example details - you can enhance with actual salary calculations
        sb.append("Basic Salary: ").append("₱20,000.00").append("\n");
        sb.append("Deductions: ").append("₱2,000.00").append("\n");
        sb.append("Net Pay: ").append("₱18,000.00").append("\n");

        txtPayslip.setText(sb.toString());
    }
}
