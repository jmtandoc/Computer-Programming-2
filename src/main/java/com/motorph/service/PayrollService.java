package com.motorph.service;

import com.motorph.database.Database;
import com.motorph.model.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class PayrollService {

    public static void generatePayslipsFromAttendance(String monthName) {
        List<Employee> employees = Database.getEmployees();

        for (Employee emp : employees) {
            double hoursWorked = getMonthlyWorkedHours(emp.getEmpId(), monthName);
            double gross = calculateGrossPay(emp, hoursWorked);
            double deductions = calculateDeductions(emp);
            LocalDate start = getPeriodStart(monthName);
            LocalDate end = getPeriodEnd(monthName);
            Payslip payslip = new Payslip(emp.getEmpId(), start, end, gross, deductions);
            Database.addPayslip(payslip);

            System.out.printf("📄 Payslip created for %s | Hours: %.2f | Net Pay: %.2f%n",
                              emp.getFullName(), hoursWorked, payslip.getNetPay());
        }
    }

    private static double getMonthlyWorkedHours(String empId, String monthName) {
        List<AttendanceLog> logs = Database.getAttendanceLogs();
        double totalHours = 0;

        int targetMonth = Month.valueOf(monthName.toUpperCase()).getValue(); // LocalDate uses 1–12

        for (AttendanceLog log : logs) {
            if (!log.getEmpId().equals(empId)) continue;

            try {
                Date date = new SimpleDateFormat("MM-dd-yyyy").parse(log.getDate());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int logMonth = cal.get(Calendar.MONTH) + 1; // Calendar uses 0–11

                if (logMonth != targetMonth) continue;

                double hours = calculateHours(log.getTimeIn(), log.getTimeOut());
                totalHours += hours;
            } catch (Exception e) {
                System.err.println("⚠️ Attendance parse error for " + empId + ": " + e.getMessage());
            }
        }

        return totalHours;
    }

    private static double calculateHours(String timeInStr, String timeOutStr) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            Date in = formatter.parse(timeInStr);
            Date out = formatter.parse(timeOutStr);
            long diffMillis = out.getTime() - in.getTime();
            return diffMillis / (1000.0 * 60 * 60); // ms to hours
        } catch (Exception e) {
            return 0;
        }
    }

    private static double calculateGrossPay(Employee e, double hours) {
        double hourlyRate = e.getBasicSalary() / 160.0;
        return (hourlyRate * hours) + e.getRiceSubsidy() + e.getPhoneAllowance() + e.getClothingAllowance();
    }

    private static double calculateDeductions(Employee e) {
        double sss = e.getBasicSalary() * 0.045;  // Example fixed deduction
        return sss + 100; // Add other fees, taxes, etc. here if needed
    }

    private static LocalDate getPeriodStart(String monthName) {
        int month = Month.valueOf(monthName.toUpperCase()).getValue();
        return LocalDate.of(LocalDate.now().getYear(), month, 1);
    }

    private static LocalDate getPeriodEnd(String monthName) {
        LocalDate firstDay = getPeriodStart(monthName);
        return firstDay.withDayOfMonth(firstDay.lengthOfMonth());
    }
}
