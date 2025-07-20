package com.motorph.ui;

import com.motorph.model.*;
import com.motorph.database.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RecordsManagerUI extends JFrame {

    public RecordsManagerUI() {
        setTitle("Manage Records");
        setSize(1280, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        // Employee Table
        JTable empTable = new JTable(getEmployeeTableModel());
        JScrollPane empScroll = new JScrollPane(empTable);
        tabs.addTab("👥 Employees", empScroll);

        // Attendance Table
        JTable attTable = new JTable(getAttendanceTableModel());
        JScrollPane attScroll = new JScrollPane(attTable);
        tabs.addTab("📅 Attendance", attScroll);

        // Payslip Table (optional — if available)
        JTable payTable = new JTable(getPayslipTableModel());
        JScrollPane payScroll = new JScrollPane(payTable);
        tabs.addTab("💰 Payslips", payScroll);

        add(tabs);
        setVisible(true);
    }

    private DefaultTableModel getEmployeeTableModel() {
        String[] columns = {
            "Emp ID", "Name", "Dept", "Position", "Role",
            "Supervisor", "Birthday", "Phone", "Address", "Status",
            "SSS", "PhilHealth", "TIN", "Pag-IBIG",
            "Basic", "Rice", "Phone Allow", "Clothing"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Employee e : Database.getEmployees()) {
            model.addRow(new Object[] {
                e.getEmpId(), e.getFullName(), e.getDepartment(), e.getPosition(), e.getRole(),
                e.getSupervisorId(), e.getBirthday(), e.getPhoneNumber(), e.getAddress(), e.getCivilStatus(),
                e.getSssNumber(), e.getPhilhealthNumber(), e.getTinNumber(), e.getPagibigNumber(),
                e.getBasicSalary(), e.getRiceSubsidy(), e.getPhoneAllowance(), e.getClothingAllowance()
            });
        }

        return model;
    }

    private DefaultTableModel getAttendanceTableModel() {
        String[] columns = {"Emp ID", "Date", "Time In", "Time Out"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (AttendanceLog log : Database.getAttendanceLogs()) {
            model.addRow(new Object[] {
                log.getEmpId(), log.getDate(), log.getTimeIn(), log.getTimeOut()
            });
        }

        return model;
    }

    private DefaultTableModel getPayslipTableModel() {
        String[] columns = {"Payslip ID", "Emp ID", "Start", "End", "Gross", "Deductions", "Net"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Payslip p : Database.getPayslips()) {
            model.addRow(new Object[] {
                p.getPayslipId(), p.getEmpId(), p.getPeriodStart(), p.getPeriodEnd(),
                p.getGrossPay(), p.getDeductions(), p.getNetPay()
            });
        }

        return model;
    }
}
