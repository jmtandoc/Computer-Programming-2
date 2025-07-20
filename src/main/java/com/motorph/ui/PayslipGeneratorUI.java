package com.motorph.ui;

import com.motorph.model.Employee;
import com.motorph.model.Payslip;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PayslipGeneratorUI extends JFrame {

    public PayslipGeneratorUI(Employee emp) {
        setTitle("Payslip Generator");
        setSize(400, 360);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 2, 10, 8));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        double basic = emp.getBasicSalary();
        double rice = emp.getRiceSubsidy();
        double phone = emp.getPhoneAllowance();
        double clothing = emp.getClothingAllowance();
        double gross = basic + rice + phone + clothing;

        double sss = basic * 0.045;
        double philhealth = basic * 0.035;
        double pagibig = basic * 0.02;
        double deductions = sss + philhealth + pagibig;

        double net = gross - deductions;

        Payslip slip = new Payslip(emp.getEmpId(), LocalDate.now(), LocalDate.now(), gross, deductions);

        add(new JLabel("Employee ID:")); add(new JLabel(emp.getEmpId()));
        add(new JLabel("Name:")); add(new JLabel(emp.getFullName()));
        add(new JLabel("Basic Salary:")); add(new JLabel("₱ " + basic));
        add(new JLabel("Gross Pay:")); add(new JLabel("₱ " + slip.getGrossPay()));
        add(new JLabel("Deductions:")); add(new JLabel("₱ " + slip.getDeductions()));
        add(new JLabel("Net Pay:")); add(new JLabel("₱ " + slip.getNetPay()));

        setVisible(true);
    }
}
