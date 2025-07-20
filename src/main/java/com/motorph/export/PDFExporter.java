package com.motorph.export;

import com.motorph.model.Employee;
import com.motorph.model.Payslip;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

public class PDFExporter {

    public static void exportPayslip(Employee emp, Payslip slip) {
        try {
            Document doc = new Document();
            String filename = "Payslip_" + emp.getEmpId() + ".pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(filename));
            doc.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12);

            doc.add(new Paragraph("Payslip for " + emp.getFullName(), titleFont));
            doc.add(new Paragraph("Employee ID: " + emp.getEmpId(), bodyFont));
            doc.add(new Paragraph("Department: " + emp.getDepartment(), bodyFont));
            doc.add(new Paragraph("Position: " + emp.getPosition(), bodyFont));
            doc.add(new Paragraph("Role: " + emp.getRole().name(), bodyFont));
            doc.add(new Paragraph("Period: " + slip.getPeriodStart() + " to " + slip.getPeriodEnd(), bodyFont));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            table.addCell("Gross Pay"); table.addCell("₱ " + slip.getGrossPay());
            table.addCell("Deductions"); table.addCell("₱ " + slip.getDeductions());
            table.addCell("Net Pay"); table.addCell("₱ " + slip.getNetPay());

            doc.add(table);
            doc.close();

        } catch (Exception e) {
            System.err.println("❌ Error exporting PDF: " + e.getMessage());
        }
    }
}
