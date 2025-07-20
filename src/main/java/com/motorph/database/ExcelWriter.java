package com.motorph.database;

import com.motorph.model.AttendanceLog;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class ExcelWriter {

    public static void saveAttendanceToExcel(String filePath) {
        try {
            File file = new File(filePath);
            Workbook workbook;
            Sheet sheet;

            // If the file exists, load it — otherwise create new workbook/sheet
            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fis);
                }
            } else {
                workbook = new XSSFWorkbook();
            }

            sheet = workbook.getSheet("Attendance Record");
            if (sheet == null) sheet = workbook.createSheet("Attendance Record");

            int rowIndex = sheet.getLastRowNum() + 1;
            List<AttendanceLog> logs = Database.getAttendanceLogs();
            for (AttendanceLog log : logs) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(log.getEmpId());
                row.createCell(1).setCellValue(log.getDate());
                row.createCell(2).setCellValue(log.getTimeIn());
                row.createCell(3).setCellValue(log.getTimeOut());
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                System.out.println("✅ Attendance saved to Excel: " + filePath);
            }

            workbook.close();

        } catch (IOException e) {
            System.err.println("❌ Error writing to Excel: " + e.getMessage());
        }
    }
}
