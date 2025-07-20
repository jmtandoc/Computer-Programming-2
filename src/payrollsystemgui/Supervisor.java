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
public class Supervisor extends User {
    private List<String> employeesUsernames;

    public Supervisor(String username, String fullName, String password, boolean firstTimeLogin) {
        super(username, fullName, password, firstTimeLogin, Role.SUPERVISOR);
        employeesUsernames = new ArrayList<>();
    }

    public void addEmployee(String employeeUsername) {
        employeesUsernames.add(employeeUsername);
    }

    public List<String> getEmployeesUsernames() {
        return employeesUsernames;
    }
}
