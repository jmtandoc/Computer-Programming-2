package com.motorph.ui;

import com.motorph.model.*;
import com.motorph.database.Database;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LandingPageUI extends JFrame {

    private final Employee emp;

    public LandingPageUI(User user) {
        emp = Database.getEmployeeByUsername(user.getUsername());

        setTitle("MotorPH Dashboard");
        setSize(880, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🕓 Right Side Clock + Time Controls
        JLabel clockLabel = new JLabel(getCurrentTime(), SwingConstants.CENTER);
        clockLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        Timer timer = new Timer(1000, e -> clockLabel.setText(getCurrentTime()));
        timer.start();

        JButton timeInBtn = new JButton("Time In");
        JButton timeOutBtn = new JButton("Time Out");

        Font btnFont = new Font("SansSerif", Font.PLAIN, 12);
        Dimension btnSize = new Dimension(100, 30);
        timeInBtn.setFont(btnFont);
        timeOutBtn.setFont(btnFont);
        timeInBtn.setPreferredSize(btnSize);
        timeOutBtn.setPreferredSize(btnSize);

        timeInBtn.addActionListener(e -> recordAttendance("IN"));
        timeOutBtn.addActionListener(e -> recordAttendance("OUT"));

        JPanel clockPanel = new JPanel();
        clockPanel.setLayout(new BoxLayout(clockPanel, BoxLayout.Y_AXIS));
        clockPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        clockPanel.add(clockLabel);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonRow.add(timeInBtn);
        buttonRow.add(timeOutBtn);
        clockPanel.add(buttonRow);

        // 👋 Left Side: Employee Info Panel
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

        // ↔️ Combine Left and Right into Center
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(leftPanel);
        centerPanel.add(clockPanel);
        add(centerPanel, BorderLayout.CENTER);

        // 🧭 Action Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());

        JButton leaveBtn = new JButton("📄 File Leave");
        JButton compBtn = new JButton("💰 Compensation");
        JButton logoutBtn = new JButton("🔓 Logout");

        leaveBtn.addActionListener(e -> new LeaveRequestUI(emp));
        compBtn.addActionListener(e -> new CompensationUI(emp));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginUI();
        });

        bottomPanel.add(leaveBtn);
        bottomPanel.add(compBtn);
        bottomPanel.add(logoutBtn);

        // 🔐 Admin Access
        boolean isAdminDept = emp.getDepartment().equalsIgnoreCase("HR")
                            || emp.getDepartment().equalsIgnoreCase("IT")
                            || emp.getDepartment().equalsIgnoreCase("Executive");

        if (isAdminDept || emp.getRole() == Role.ADMIN) {
            JButton manageBtn = new JButton("🧾 Manage Records");
            manageBtn.addActionListener(e -> new AdminPanelUI());
            bottomPanel.add(manageBtn);
        }

        if (emp.getRole() == Role.ADMIN
            || emp.getRole() == Role.SUPERVISOR
            || emp.getDepartment().equalsIgnoreCase("HR")) {
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
