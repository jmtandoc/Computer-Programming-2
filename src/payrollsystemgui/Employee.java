/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystemgui;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jhoan
 */
public class Employee extends User {
    private String position;
    private String supervisorUsername;

    public Employee(String username, String fullName, String password, boolean firstTimeLogin, String position, String supervisorUsername) {
        super(username, fullName, password, firstTimeLogin, Role.EMPLOYEE);
        this.position = position;
        this.supervisorUsername = supervisorUsername;
    }

    public String getPosition() {
        return position;
    }

    public String getSupervisorUsername() {
        return supervisorUsername;
    }
}
