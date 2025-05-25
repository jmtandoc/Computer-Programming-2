    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package payrollsystemgui;

import java.time.LocalDate;

/**
 *
 * @author jhoan
 */
public class LeaveRequest {
    private String username;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // Pending, Approved, Denied
    private String managerComments;

    public LeaveRequest(String username, LeaveType leaveType, LocalDate startDate, LocalDate endDate) {
        this.username = username;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "Pending";
        this.managerComments = "";
    }

    public String getUsername() {
        return username;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManagerComments() {
        return managerComments;
    }

    public void setManagerComments(String comments) {
        this.managerComments = comments;
    }
}
   
