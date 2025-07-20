/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystemgui;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jhoan
 */
public class Attendance {
    private Map<String, LocalDateTime> loginTimes;
    private Map<String, LocalDateTime> logoutTimes;

    public Attendance() {
        loginTimes = new HashMap<>();
        logoutTimes = new HashMap<>();
    }

    public boolean logIn(User user) {
        String username = user.getUsername();
        if (loginTimes.containsKey(username) && logoutTimes.get(username) == null) {
            return false; // already logged in without logging out
        }
        loginTimes.put(username, LocalDateTime.now());
        logoutTimes.put(username, null);
        return true;
    }

    public boolean logOut(User user) {
        String username = user.getUsername();
        if (!loginTimes.containsKey(username) || (logoutTimes.get(username) != null)) {
            return false; // must log in first or already logged out
        }
        logoutTimes.put(username, LocalDateTime.now());
        return true;
    }

    public LocalDateTime getLastLogInTime(User user) {
        return loginTimes.get(user.getUsername());
    }

    public LocalDateTime getLastLogOutTime(User user) {
        return logoutTimes.get(user.getUsername());
    }
}
