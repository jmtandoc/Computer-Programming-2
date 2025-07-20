package com.motorph.model;

import com.motorph.model.Role;
import com.motorph.model.User;

public class Employee {
    private String empId;
    private String fullName;
    private String department;
    private Role role;
    private User userAccount;

    public Employee(String empId, String fullName, String department, Role role, User userAccount) {
        this.empId = empId;
        this.fullName = fullName;
        this.department = department;
        this.role = role;
        this.userAccount = userAccount;
    }

    public String getEmpId() {
        return empId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public Role getRole() {
        return role;
    }

    public User getUserAccount() {
        return userAccount;
    }
}
