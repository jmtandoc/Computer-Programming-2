package com.motorph.model;

public class AttendanceLog {
    private String empId;
    private String date;
    private String timeIn;
    private String timeOut;

    public AttendanceLog(String empId, String date, String timeIn, String timeOut) {
        this.empId = empId;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public String getEmpId() { return empId; }
    public String getDate() { return date; }
    public String getTimeIn() { return timeIn; }
    public String getTimeOut() { return timeOut; }

    public void setTimeOut(String timeOut) { this.timeOut = timeOut; }
}
