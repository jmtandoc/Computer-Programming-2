package com.motorph.database;

import com.motorph.model.Role;
import com.motorph.model.Employee;
import com.motorph.model.User;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Database {
    private static List<User> users = new ArrayList<>();
    private static List<Employee> employees = new ArrayList<>();

    public static void loadFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet userSheet = workbook.getSheet("Users");
            Sheet employeeSheet = workbook.getSheet("Employee Details");

            for (Row row : userSheet) {
                if (row.getRowNum() == 0) continue;

                String username = getCellAsString(row.getCell(0)).trim();
                String password = getCellAsString(row.getCell(1)).trim();
                String roleStr = getCellAsString(row.getCell(2)).trim().toUpperCase();

                try {
                    Role role = Role.valueOf(roleStr);
                    users.add(new User(username, password, role));
                    System.out.println("📦 Loaded User: " + username + " / " + password + " / " + roleStr);
                } catch (IllegalArgumentException e) {
                    System.err.println("❌ Invalid role in Users sheet: " + roleStr);
                }
            }

            for (Row row : employeeSheet) {
                if (row.getRowNum() == 0) continue;

                String empId = getCellAsString(row.getCell(0)).trim();
                String lastName = getCellAsString(row.getCell(1)).trim();
                String firstName = getCellAsString(row.getCell(2)).trim();
                String fullName = firstName + " " + lastName;
                String department = getCellAsString(row.getCell(21)).trim();
                String username = getCellAsString(row.getCell(19)).trim();
                String roleStr = getCellAsString(row.getCell(20)).trim().toUpperCase();

                try {
                    Role role = Role.valueOf(roleStr);
                    User linkedUser = findUserByUsername(username);
                    employees.add(new Employee(empId, fullName, department, role, linkedUser));
                } catch (IllegalArgumentException e) {
                    System.err.println("❌ Invalid role in Employee Details: " + roleStr);
                }
            }

        } catch (IOException e) {
            System.err.println("Error loading Excel file: " + e.getMessage());
        }
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Employee> getEmployees() {
        return employees;
    }

    public static User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username.trim())) {
                return user;
            }
        }
        return null;
    }

    public static Employee getEmployeeByUsername(String username) {
        for (Employee emp : employees) {
            if (emp.getUserAccount() != null &&
                emp.getUserAccount().getUsername().equalsIgnoreCase(username.trim())) {
                return emp;
            }
        }
        return null;
    }

    private static String getCellAsString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}
