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
public class ChangePasswordDialog extends JDialog {
    private JPasswordField pwdNew;
    private JPasswordField pwdConfirm;
    private JButton btnChange;

    private User user;
    private AuthService authService;

    public ChangePasswordDialog(JFrame parent, User user, AuthService authService) {
        super(parent, "Change Password", true);
        this.user = user;
        this.authService = authService;

        setSize(350, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(3, 2, 10, 10));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        add(new JLabel("New Password:"));
        pwdNew = new JPasswordField();
        add(pwdNew);

        add(new JLabel("Confirm Password:"));
        pwdConfirm = new JPasswordField();
        add(pwdConfirm);

        btnChange = new JButton("Change Password");
        add(new JLabel());
        add(btnChange);

        btnChange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPass = new String(pwdNew.getPassword());
                String confirmPass = new String(pwdConfirm.getPassword());

                if (newPass.isEmpty() || confirmPass.isEmpty()) {
                    JOptionPane.showMessageDialog(ChangePasswordDialog.this, "Please fill both fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!newPass.equals(confirmPass)) {
                    JOptionPane.showMessageDialog(ChangePasswordDialog.this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                authService.changePassword(user, newPass);
                JOptionPane.showMessageDialog(ChangePasswordDialog.this, "Password changed successfully.");
                dispose();
                ((LoginScreen) parent).openLandingPage(user);
            }
        });
    }
}
