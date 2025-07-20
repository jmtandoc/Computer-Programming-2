/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystemgui;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jhoan
 */
public class AuthService {
    private Map<String, User> users;

    public AuthService() {
        users = new HashMap<>();
        // Initialize users - replace with Excel read later
        users.put("admin", new Admin("admin", "Administrator", "admin123", false));
        users.put("john", new Employee("john", "John Doe", "password", true, "Developer", "supervisor1"));
        users.put("supervisor1", new Supervisor("supervisor1", "Jane Smith", "super123", false));
    }

    public User authenticate(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        users.put(user.getUsername(), user);
    }
}
