package com.motorph.database;

import com.motorph.model.AttendanceLog;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class ExcelWriter {

    public static void saveAttendanceToExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Attendance Record");
            if (sheet == null) sheet = workbook.createSheet("Attendance Record");

            int rowIndex = sheet.getLastRowNum() + 1;
            for (AttendanceLog log : Database.getAttendanceLogs()) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(log.getEmpId());
                row.createCell(1).setCellValue(log.getDate());
                row.createCell(2).setCellValue(log.getTimeIn());
                row.createCell(3).setCellValue(log.getTimeOut());
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

        } catch (IOException e) {
            System.err.println("❌ Error writing to Excel: " + e.getMessage());
        }
    }
}
