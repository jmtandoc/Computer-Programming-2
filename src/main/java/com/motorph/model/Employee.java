package com.motorph.model;

public class Employee {
    private String empId;
    private String fullName;
    private String position;
    private String supervisorId;
    private User userAccount;
    private String department;
    private Role role;

    // Optional details
    private String birthday;
    private String address;
    private String phoneNumber;
    private String civilStatus;
    private String sssNumber;
    private String philhealthNumber;
    private String tinNumber;
    private String pagibigNumber;

    // 💰 Salary-related
    private double basicSalary;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;

    public Employee(String empId, String fullName, String position, String supervisorId, User user, String department, Role role) {
        this.empId = empId;
        this.fullName = fullName;
        this.position = position;
        this.supervisorId = supervisorId;
        this.userAccount = user;
        this.department = department;
        this.role = role;
    }

    // ✅ Standard Getters
    public String getEmpId() { return empId; }
    public String getFullName() { return fullName; }
    public String getPosition() { return position; }
    public String getSupervisorId() { return supervisorId; }
    public String getDepartment() { return department; }
    public User getUserAccount() { return userAccount; }
    public Role getRole() { return role; }

    // 📋 Personal Info
    public String getBirthday() { return birthday; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCivilStatus() { return civilStatus; }
    public String getSssNumber() { return sssNumber; }
    public String getPhilhealthNumber() { return philhealthNumber; }
    public String getTinNumber() { return tinNumber; }
    public String getPagibigNumber() { return pagibigNumber; }

    // ✅ Setters for personal info
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setCivilStatus(String civilStatus) { this.civilStatus = civilStatus; }
    public void setSssNumber(String sssNumber) { this.sssNumber = sssNumber; }
    public void setPhilhealthNumber(String philhealthNumber) { this.philhealthNumber = philhealthNumber; }
    public void setTinNumber(String tinNumber) { this.tinNumber = tinNumber; }
    public void setPagibigNumber(String pagibigNumber) { this.pagibigNumber = pagibigNumber; }

    // 💸 Salary Getters
    public double getBasicSalary() { return basicSalary; }
    public double getRiceSubsidy() { return riceSubsidy; }
    public double getPhoneAllowance() { return phoneAllowance; }
    public double getClothingAllowance() { return clothingAllowance; }

    // 💸 Salary Setters
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }
    public void setRiceSubsidy(double riceSubsidy) { this.riceSubsidy = riceSubsidy; }
    public void setPhoneAllowance(double phoneAllowance) { this.phoneAllowance = phoneAllowance; }
    public void setClothingAllowance(double clothingAllowance) { this.clothingAllowance = clothingAllowance; }
}
