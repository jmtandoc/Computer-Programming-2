package com.motorph.model;

public class User {
    private String username;
    private String password;
    private boolean active;
    private Role role;
    private boolean firstTimeLogin;

    public User(String username, String password, boolean active, Role role) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.role = role;
        this.firstTimeLogin = true;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isActive() { return active; }
    public Role getRole() { return role; }

    public boolean isFirstTimeLogin() { return firstTimeLogin; }
    public void setFirstTimeLogin(boolean firstTimeLogin) {
        this.firstTimeLogin = firstTimeLogin;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
