package com.motorph.model;

import java.time.LocalDate;
import java.util.UUID;

public class Payslip {
    private final String payslipId;
    private final String empId;
    private final LocalDate periodStart;
    private final LocalDate periodEnd;
    private final double grossPay;
    private final double deductions;
    private final double netPay;

    public Payslip(String empId, LocalDate periodStart, LocalDate periodEnd,
                   double grossPay, double deductions) {
        this.payslipId = UUID.randomUUID().toString();
        this.empId = empId;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.grossPay = grossPay;
        this.deductions = deductions;
        this.netPay = grossPay - deductions;
    }

    public String getPayslipId() {
        return payslipId;
    }

    public String getEmpId() {
        return empId;
    }

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public double getDeductions() {
        return deductions;
    }

    public double getNetPay() {
        return netPay;
    }
}
