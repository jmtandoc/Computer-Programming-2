package com.motorph.service;

import com.motorph.model.User;
import java.util.List;

public class AuthService {
    private final List<User> userList;

    public AuthService(List<User> userList) {
        this.userList = userList;
    }

    public User login(String username, String password) {
        if (username == null || password == null) return null;

        for (User user : userList) {
            String storedUsername = user.getUsername().trim();
            String storedPassword = user.getPassword().trim();

            if (storedUsername.equalsIgnoreCase(username.trim()) &&
                storedPassword.equals(password.trim())) {
                System.out.println("✅ Matched User: " + storedUsername);
                return user;
            }
        }

        System.out.println("❌ No matching user for: " + username);
        return null;
    }
}
