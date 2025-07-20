package com.motorph.util;

import com.motorph.model.Payslip;
import java.io.FileWriter;
import java.io.IOException;

public class PayslipExporter {

    public static void exportPayslip(Payslip p) {
        String filename = "Payslip_" + p.getEmpId() + "_" + p.getPeriodStart() + ".txt";

        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("Payslip for " + p.getEmpId() + "\n");
            fw.write("Period: " + p.getPeriodStart() + " to " + p.getPeriodEnd() + "\n");
            fw.write("Gross Pay: " + p.getGrossPay() + "\n");
            fw.write("Deductions: " + p.getDeductions() + "\n");
            fw.write("Net Pay: " + p.getNetPay() + "\n");

            fw.flush();
            System.out.println("✅ Payslip exported to " + filename);
        } catch (IOException e) {
            System.err.println("❌ Error exporting payslip: " + e.getMessage());
        }
    }
}
