package com.motorph.model;

import java.time.LocalDate;

public class LeaveRequest {
    private String leaveId;
    private String empId;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;

    public LeaveRequest(String leaveId, String empId, String type, LocalDate startDate, LocalDate endDate, String reason) {
        this.leaveId = leaveId;
        this.empId = empId;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = "Pending";
    }

    public String getLeaveId() { return leaveId; }
    public String getEmpId() { return empId; }
    public String getType() { return type; }
    public String getLeaveType() { return type; } // compatibility alias
    public String getReason() { return reason; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
    public void approve() { this.status = "Approved"; }
    public void reject() { this.status = "Rejected"; }
}
