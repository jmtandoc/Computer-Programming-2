package com.motorph.service;

import com.motorph.database.Database;
import com.motorph.model.Role;
import com.motorph.model.User;

public class AuthService {
    public static User authenticate(String username, String password) {
        User user = Database.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public static boolean isFirstTimeLogin(User user) {
        return user.isFirstTimeLogin();
    }

    public static Role getUserRole(User user) {
        return user.getRole();
    }

    public static void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        user.setFirstTimeLogin(false);
    }
}
