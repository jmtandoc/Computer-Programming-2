/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystemgui;

/**
 *
 * @author jhoan
 */
public abstract class User {
    protected String username;
    protected String fullName;
    protected String password;
    protected boolean firstTimeLogin;
    protected Role role;

    public User(String username, String fullName, String password, boolean firstTimeLogin, Role role) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.firstTimeLogin = firstTimeLogin;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isFirstTimeLogin() {
        return firstTimeLogin;
    }

    public void setPassword(String password) {
        this.password = password;
        this.firstTimeLogin = false;
    }

    public Role getRole() {
        return role;
    }
}

