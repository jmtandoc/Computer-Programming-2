/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystemgui;

/**
 *
 * @author jhoan
 */
public class Admin extends User {
    public Admin(String username, String fullName, String password, boolean firstTimeLogin) {
        super(username, fullName, password, firstTimeLogin, Role.ADMIN);
    }
}
 
