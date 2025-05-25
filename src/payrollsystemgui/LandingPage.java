/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystemgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author jhoan
 */
public class LandingPage extends JFrame {
    private User user;

    private JLabel lblWelcome;
    private JButton btnLogIn, btnLogOut, btnManageLeaves, btnViewPayslips;

    private Attendance attendance;

    public LandingPage(User user) {
        this.user = user;

        setTitle("Payroll System - Landing Page");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        attendance = new Attendance();

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblWelcome = new JLabel("Welcome, " + user.getFullName());
        panel.add(lblWelcome);

        btnLogIn = new JButton("Log In");
        btnLogOut = new JButton("Log Out");
        btnManageLeaves = new JButton("Manage Leaves");
        btnViewPayslips = new JButton("View Payslips");

        panel.add(btnLogIn);
        panel.add(btnLogOut);
        panel.add(btnManageLeaves);
        panel.add(btnViewPayslips);

        add(panel);

        btnLogIn.addActionListener(e -> {
            if (attendance.logIn(user)) {
                JOptionPane.showMessageDialog(this, "Log in recorded at " + attendance.getLastLogInTime(user));
            } else {
                JOptionPane.showMessageDialog(this, "Already logged in.");
            }
        });

        btnLogOut.addActionListener(e -> {
            if (attendance.logOut(user)) {
                JOptionPane.showMessageDialog(this, "Log out recorded at " + attendance.getLastLogOutTime(user));
            } else {
                JOptionPane.showMessageDialog(this, "You must log in first.");
            }
        });

        btnManageLeaves.addActionListener(e -> {
            LeaveManagement leaveMgmt = new LeaveManagement(user);
            leaveMgmt.setVisible(true);
        });

        btnViewPayslips.addActionListener(e -> {
            Payslip payslip = new Payslip(user);
            payslip.setVisible(true);
        });
    }
}
