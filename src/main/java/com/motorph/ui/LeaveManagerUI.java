package com.motorph.ui;

import com.motorph.model.User;
import com.motorph.model.LeaveRequest;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LeaveManagerUI extends JFrame {

    public LeaveManagerUI(User user, List<LeaveRequest> leaveRequests) {
        setTitle("Leave Manager");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Welcome, " + user.getUsername() + " (" + user.getRole() + ")"), BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (LeaveRequest req : leaveRequests) {
            model.addElement(req.getEmpId() + " - " + req.getLeaveType() + " (" + req.getStatus() + ")");
        }

        JList<String> requestList = new JList<>(model);
        panel.add(new JScrollPane(requestList), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");

        approveBtn.addActionListener(e -> {
            int selected = requestList.getSelectedIndex();
            if (selected != -1) {
                leaveRequests.get(selected).approve();
                model.set(selected, model.get(selected).replace("Pending", "Approved"));
            }
        });

        rejectBtn.addActionListener(e -> {
            int selected = requestList.getSelectedIndex();
            if (selected != -1) {
                leaveRequests.get(selected).reject();
                model.set(selected, model.get(selected).replace("Pending", "Rejected"));
            }
        });

        btnPanel.add(approveBtn);
        btnPanel.add(rejectBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
