package com.motorph.ui;

import com.motorph.database.Database;
import com.motorph.database.ExcelWriter;
import com.motorph.model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LandingPageUI extends JFrame {

    private final Employee emp;

    public LandingPageUI(User user) {
        emp = Database.getEmployeeByUsername(user.getUsername());

        setTitle("MotorPH Dashboard");
        setSize(960, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🕒 Clock + Time Controls Panel
        JLabel clockLabel = new JLabel(getCurrentTime(), SwingConstants.CENTER);
        clockLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        Timer timer = new Timer(1000, e -> clockLabel.setText(getCurrentTime()));
        timer.start();

        JButton timeInBtn = new JButton("Time In");
        JButton timeOutBtn = new JButton("Time Out");
        JButton logoutBtn = new JButton("Logout");

        Font btnFont = new Font("SansSerif", Font.PLAIN, 12);
        Dimension btnSize = new Dimension(100, 30);
        timeInBtn.setFont(btnFont);
        timeOutBtn.setFont(btnFont);
        logoutBtn.setFont(btnFont);
        timeInBtn.setPreferredSize(btnSize);
        timeOutBtn.setPreferredSize(btnSize);
        logoutBtn.setPreferredSize(btnSize);

        timeInBtn.addActionListener(e -> recordAttendance("IN"));
        timeOutBtn.addActionListener(e -> recordAttendance("OUT"));
        logoutBtn.addActionListener(e -> {
            ExcelWriter.saveAttendanceToExcel("src/main/resources/MotorPH.xlsx"); // Auto-save attendance
            dispose();
            new LoginUI();
        });

        JPanel clockPanel = new JPanel();
        clockPanel.setLayout(new BoxLayout(clockPanel, BoxLayout.Y_AXIS));
        clockPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        clockPanel.add(clockLabel);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonRow.add(timeInBtn);
        buttonRow.add(timeOutBtn);
        buttonRow.add(logoutBtn);
        clockPanel.add(buttonRow);

        // 👤 Employee Info Panel
        JPanel leftPanel = new JPanel(new GridLayout(10, 1, 5, 5));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 10));

        JLabel welcomeLabel = new JLabel("Welcome, " + emp.getFullName());
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        leftPanel.add(welcomeLabel);
        leftPanel.add(new JLabel("Birthday: " + safe(emp.getBirthday())));
        leftPanel.add(new JLabel("Address: " + safe(emp.getAddress())));
        leftPanel.add(new JLabel("Phone Number: " + safe(emp.getPhoneNumber())));
        leftPanel.add(new JLabel("Status: " + safe(emp.getCivilStatus())));
        leftPanel.add(new JLabel("Immediate Supervisor: " + safe(emp.getSupervisorId())));
        leftPanel.add(new JLabel("Position: " + safe(emp.getPosition())));
        leftPanel.add(new JLabel("Department: " + safe(emp.getDepartment())));
        leftPanel.add(new JLabel("Role: " + safe(emp.getRole().toString())));
        leftPanel.add(new JLabel("Employee ID: " + safe(emp.getEmpId())));

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(leftPanel);
        centerPanel.add(clockPanel);
        add(centerPanel, BorderLayout.CENTER);

        // 🧭 Bottom Panel with Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton leaveBtn = new JButton("📄 File Leave");
        JButton compBtn = new JButton("💰 Compensation");
        JButton attendanceBtn = new JButton("📅 Attendance History");
        JButton changePassBtn = new JButton("🔐 Change Password");

        leaveBtn.addActionListener(e -> new LeaveRequestUI(emp));
        compBtn.addActionListener(e -> new CompensationUI(emp));
        attendanceBtn.addActionListener(e -> new AttendanceViewerUI(emp));
        changePassBtn.addActionListener(e -> new PasswordChangeUI(emp.getUserAccount()));

        bottomPanel.add(leaveBtn);
        bottomPanel.add(compBtn);
        bottomPanel.add(attendanceBtn);
        bottomPanel.add(changePassBtn);

        // 🔐 Conditional Buttons
        boolean canManage = emp.getRole() == Role.ADMIN &&
                            (emp.getDepartment().equalsIgnoreCase("HR") ||
                             emp.getDepartment().equalsIgnoreCase("IT") ||
                             emp.getDepartment().equalsIgnoreCase("Executive"));

        if (canManage) {
            JButton manageBtn = new JButton("🧾 Manage Records");
            manageBtn.addActionListener(e -> new AdminPanelUI());
            bottomPanel.add(manageBtn);

            JButton employeeBtn = new JButton("👥 Manage Employees");
            employeeBtn.addActionListener(e -> new EmployeeManagerUI());
            bottomPanel.add(employeeBtn);
        }

        boolean isApprover = emp.getRole() == Role.ADMIN ||
                             emp.getRole() == Role.SUPERVISOR ||
                            (emp.getDepartment().equalsIgnoreCase("HR") &&
                             emp.getRole() != Role.EMPLOYEE);

        if (isApprover) {
            JButton approveBtn = new JButton("✅ Approve Leaves");
            approveBtn.addActionListener(e -> new LeaveApprovalUI(emp));
            bottomPanel.add(approveBtn);
        }

        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void recordAttendance(String type) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        AttendanceLog log = new AttendanceLog(emp.getEmpId(), date,
                type.equals("IN") ? time : "", type.equals("OUT") ? time : "");
        Database.addAttendanceLog(log);
        JOptionPane.showMessageDialog(this, "✅ Time " + type + " recorded at " + time);
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy  HH:mm:ss"));
    }

    private String safe(String value) {
        return (value == null || value.isBlank()) ? "N/A" : value;
    }
}
