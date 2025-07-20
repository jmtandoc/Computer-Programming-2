package com.motorph.model;

import java.time.LocalDate;

public class Payslip {
    private String payslipId;
    private String empId;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private double grossPay;
    private double deductions;
    private double netPay;

    public Payslip(String empId, LocalDate periodStart, LocalDate periodEnd, double grossPay, double deductions) {
        this.payslipId = empId + "-" + periodEnd; // example format
        this.empId = empId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.grossPay = grossPay;
        this.deductions = deductions;
        this.netPay = grossPay - deductions;
    }

    public String getPayslipId() { return payslipId; }
    public String getEmpId() { return empId; }
    public LocalDate getPeriodStart() { return periodStart; }
    public LocalDate getPeriodEnd() { return periodEnd; }
    public double getGrossPay() { return grossPay; }
    public double getDeductions() { return deductions; }
    public double getNetPay() { return netPay; }
}
