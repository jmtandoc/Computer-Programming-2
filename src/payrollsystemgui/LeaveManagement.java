/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystemgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jhoan
 */
public class LeaveManagement extends JFrame {
    private User user;
    private List<LeaveRequest> leaveRequests;

    private JComboBox<LeaveType> cbLeaveType;
    private JTextField txtStartDate, txtEndDate;
    private JButton btnSubmitRequest;
    private JTextArea txtStatus;

    public LeaveManagement(User user) {
        this.user = user;
        this.leaveRequests = new ArrayList<>();

        setTitle("Leave Management");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new JLabel("Leave Type:"));
        cbLeaveType = new JComboBox<>(LeaveType.values());
        add(cbLeaveType);

        add(new JLabel("Start Date (YYYY-MM-DD):"));
        txtStartDate = new JTextField();
        add(txtStartDate);

        add(new JLabel("End Date (YYYY-MM-DD):"));
        txtEndDate = new JTextField();
        add(txtEndDate);

        btnSubmitRequest = new JButton("Submit Leave Request");
        add(btnSubmitRequest);

        txtStatus = new JTextArea();
        txtStatus.setEditable(false);
        add(new JScrollPane(txtStatus));

        btnSubmitRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitLeaveRequest();
            }
        });
    }

    private void submitLeaveRequest() {
        try {
            LeaveType type = (LeaveType) cbLeaveType.getSelectedItem();
            LocalDate start = LocalDate.parse(txtStartDate.getText());
            LocalDate end = LocalDate.parse(txtEndDate.getText());

            if (end.isBefore(start)) {
                JOptionPane.showMessageDialog(this, "End date cannot be before start date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LeaveRequest request = new LeaveRequest(user.getUsername(), type, start, end);
            leaveRequests.add(request);
            txtStatus.append("Leave request submitted: " + type + " from " + start + " to " + end + "\n");
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
   
