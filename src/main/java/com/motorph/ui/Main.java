package com.motorph;

import com.motorph.database.Database;
import com.motorph.service.PayrollService;
import com.motorph.ui.LoginUI;
import com.motorph.model.User;

import javax.swing.SwingUtilities;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("🚀 Starting Payroll System...");

            String path = "C:/Users/jhoan/OneDrive/Documents/Desktop/Investments/Documents/NetBeansProjects/MotorPHPayrollFixed/src/MotorPH_Employee_Data.xlsx";
            File file = new File(path);
            if (!file.exists()) {
                System.err.println("❌ Excel file not found at: " + path);
                return;
            }

            // 🧠 Load employee and attendance records
            Database.loadFromExcel(path);
            Database.loadAttendanceFromExcel(path);

            // 🧮 Generate payslips for the selected month
            PayrollService.generatePayslipsFromAttendance("July");

            // 📋 Print loaded users for login debug
            System.out.println("👥 Users loaded:");
            for (User u : Database.getUsers()) {
                System.out.println("→ " + u.getUsername() + " / " + u.getPassword());
            }

            // 🎬 Launch login GUI
            new LoginUI();
        });
    }
}
