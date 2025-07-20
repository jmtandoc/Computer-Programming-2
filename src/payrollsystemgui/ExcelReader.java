/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystemgui;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jhoan
 */
public class ExcelReader {
    public static Map<String, User> readUsersFromExcel("src/MotorPH_Employee Data.xlsx") {
        Map<String, User> users = new HashMap<>();
        try (FileInputStream fis = new FileInputStream("src/MotorPH Employee Data.xlsx");
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header

                String username = row.getCell(19).getStringCellValue();
                String fullName = row.getCell(21).getStringCellValue();
                String password = row.getCell(20).getStringCellValue();
                String roleStr = row.getCell(13).getStringCellValue();
                boolean firstTime = row.getCell(22).getBooleanCellValue();

                Role role = Role.valueOf(roleStr.toUpperCase());

                User user = null;
                switch (role) {
                    case ADMIN:
                        user = new Admin(username, fullName, password, firstTime);
                        break;
                    case PAYROLL:
                        user = new Payroll(username, fullName, password, firstTime);
                        break;
                    case SUPERVISOR:
                        user = new Supervisor(username, fullName, password, firstTime);
                        break;
                    case EMPLOYEE:
                        // Assuming position and supervisor username are also in Excel (columns 5 and 6)
                        String position = row.getCell(5).getStringCellValue();
                        String supervisorUsername = row.getCell(6).getStringCellValue();
                        user = new Employee(username, fullName, password, firstTime, position, supervisorUsername);
                        break;
                }
                if (user != null) {
                    users.put(username, user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
