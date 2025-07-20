package com.motorph.database;

import com.motorph.model.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Database {

    private static final List<User> users = new ArrayList<>();
    private static final List<Employee> employees = new ArrayList<>();
    private static final List<AttendanceLog> attendanceLogs = new ArrayList<>();
    private static final List<LeaveRequest> leaveRequests = new ArrayList<>();
    private static final List<Payslip> payslips = new ArrayList<>();

    public static void loadFromExcel(String filePath) {
        loadUsersFromExcel(filePath);
        loadEmployeesFromExcel(filePath);
        loadAttendanceFromExcel(filePath);
    }

    public static void loadUsersFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Users");
            if (sheet == null) return;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String username = getCellAsString(row.getCell(0));
                String password = getCellAsString(row.getCell(1));
                String roleStr = getCellAsString(row.getCell(2));

                Role role = switch (roleStr.toUpperCase()) {
                    case "ADMIN" -> Role.ADMIN;
                    case "SUPERVISOR" -> Role.SUPERVISOR;
                    case "PAYROLL" -> Role.PAYROLL;
                    default -> Role.EMPLOYEE;
                };

                users.add(new User(username, password, true, role));
            }

        } catch (IOException e) {
            System.err.println("❌ Error loading Users sheet: " + e.getMessage());
        }
    }

    public static void loadEmployeesFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Employee Details");
            if (sheet == null) return;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String empId = getCellAsString(row.getCell(0));
                String lastName = getCellAsString(row.getCell(1));
                String firstName = getCellAsString(row.getCell(2));
                String birthday = getCellAsString(row.getCell(3));
                String address = getCellAsString(row.getCell(4));
                String phone = getCellAsString(row.getCell(5));
                String sss = getCellAsString(row.getCell(6));
                String philhealth = getCellAsString(row.getCell(7));
                String tin = getCellAsString(row.getCell(8));
                String pagibig = getCellAsString(row.getCell(9));
                String status = getCellAsString(row.getCell(10));
                String position = getCellAsString(row.getCell(11));
                String supervisor = getCellAsString(row.getCell(12));
                String basic = getCellAsString(row.getCell(13));
                String rice = getCellAsString(row.getCell(14));
                String phoneAllow = getCellAsString(row.getCell(15));
                String clothing = getCellAsString(row.getCell(16));
                String username = getCellAsString(row.getCell(19));
                String roleStr = getCellAsString(row.getCell(20));
                String dept = getCellAsString(row.getCell(21));

                Role role = switch (roleStr.toUpperCase()) {
                    case "ADMIN" -> Role.ADMIN;
                    case "SUPERVISOR" -> Role.SUPERVISOR;
                    case "PAYROLL" -> Role.PAYROLL;
                    default -> Role.EMPLOYEE;
                };

                User user = findUserByUsername(username);
                if (user == null) continue;

                Employee emp = new Employee(empId, firstName + " " + lastName, position, supervisor, user, dept, role);
                emp.setBirthday(birthday);
                emp.setAddress(address);
                emp.setPhoneNumber(phone);
                emp.setCivilStatus(status);
                emp.setSssNumber(sss);
                emp.setPhilhealthNumber(philhealth);
                emp.setTinNumber(tin);
                emp.setPagibigNumber(pagibig);

                try {
                    emp.setBasicSalary(Double.parseDouble(basic));
                    emp.setRiceSubsidy(Double.parseDouble(rice));
                    emp.setPhoneAllowance(Double.parseDouble(phoneAllow));
                    emp.setClothingAllowance(Double.parseDouble(clothing));
                } catch (NumberFormatException ignored) {}

                employees.add(emp);
            }

        } catch (IOException e) {
            System.err.println("❌ Error loading Employee Details sheet: " + e.getMessage());
        }
    }

    public static void loadAttendanceFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Attendance Record");
            if (sheet == null) return;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String empId = getCellAsString(row.getCell(0));
                Cell dateCell = row.getCell(1);
                Cell timeInCell = row.getCell(2);
                Cell timeOutCell = row.getCell(3);

                String date = formatDate(dateCell);
                String timeIn = formatTime(timeInCell);
                String timeOut = formatTime(timeOutCell);

                if (!empId.isEmpty() && !date.isEmpty()) {
                    attendanceLogs.add(new AttendanceLog(empId, date, timeIn, timeOut));
                }
            }

        } catch (IOException e) {
            System.err.println("❌ Error loading Attendance Record sheet: " + e.getMessage());
        }
    }

    private static String getCellAsString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> DateUtil.isCellDateFormatted(cell)
                    ? new SimpleDateFormat("MM-dd-yyyy").format(cell.getDateCellValue())
                    : String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };
    }

    private static String formatDate(Cell cell) {
        if (cell == null) return "";

        try {
            if (DateUtil.isCellDateFormatted(cell)) {
                return new SimpleDateFormat("MM-dd-yyyy").format(cell.getDateCellValue());
            } else if (cell.getCellType() == CellType.STRING) {
                String text = cell.getStringCellValue().trim();
                List<String> formats = Arrays.asList("MM-dd-yyyy", "yyyy-MM-dd", "MM/dd/yyyy", "MMMM d, yyyy");

                for (String pattern : formats) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        sdf.setLenient(false);
                        Date parsed = sdf.parse(text);
                        return new SimpleDateFormat("MM-dd-yyyy").format(parsed);
                    } catch (Exception ignore) {}
                }

                return text;
            }
        } catch (Exception e) {
            System.err.println("⚠️ Date parse error: " + e.getMessage());
        }

        return "";
    }

    private static String formatTime(Cell cell) {
        if (cell == null) return "";

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return new SimpleDateFormat("HH:mm").format(DateUtil.getJavaDate(cell.getNumericCellValue()));
            } else if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue().trim();
            }
        } catch (Exception e) {
            System.err.println("⚠️ Time parse error: " + e.getMessage());
        }

        return "";
    }

    public static void addAttendanceLog(AttendanceLog log) {
        attendanceLogs.add(log);
    }

    public static void addLeaveRequest(LeaveRequest lr) {
        leaveRequests.add(lr);
    }

    public static void addPayslip(Payslip p) {
        payslips.add(p);
    }

    public static void updateUserPassword(String username, String newPassword) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                u.setPassword(newPassword);
                break;
            }
        }
    }

    public static User findUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) return u;
        }
        return null;
    }

    public static Employee getEmployeeByUsername(String username) {
        for (Employee e : employees) {
            if (e.getUserAccount().getUsername().equalsIgnoreCase(username)) return e;
        }
        return null;
    }

    public static LeaveRequest findLeaveById(String id) {
        for (LeaveRequest lr : leaveRequests) {
            if (lr.getLeaveId().equalsIgnoreCase(id)) return lr;
        }
        return null;
    }

    public static List<User> getUsers() { return users; }
    public static List<Employee> getEmployees() { return employees; }
    public static List<AttendanceLog> getAttendanceLogs() { return attendanceLogs; }
    public static List<LeaveRequest> getLeaveRequests() { return leaveRequests; }
public static List<Payslip> getPayslips() { return payslips; }
}