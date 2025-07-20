/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystemgui;

/**
 *
 * @author jhoan
 */
public class Payroll extends User {
    public Payroll(String username, String fullName, String password, boolean firstTimeLogin) {
        super(username, fullName, password, firstTimeLogin, Role.PAYROLL);
    }
}
  
