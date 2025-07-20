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
public class LoginScreen extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    private AuthService authService;

    public LoginScreen() {
        setTitle("Payroll System - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        authService = new AuthService();

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        btnLogin = new JButton("Login");
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword());

                User user = authService.authenticate(username, password);
                if (user != null) {
                    if (user.isFirstTimeLogin()) {
                        // Show change password screen
                        ChangePasswordDialog changePwdDialog = new ChangePasswordDialog(LoginScreen.this, user, authService);
                        changePwdDialog.setVisible(true);
                    } else {
                        openLandingPage(user);
                    }
                } else {
                    JOptionPane.showMessageDialog(LoginScreen.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void openLandingPage(User user) {
        LandingPage landingPage = new LandingPage(user);
        landingPage.setVisible(true);
        this.dispose();
    }
}
   
