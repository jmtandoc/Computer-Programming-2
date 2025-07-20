package com.motorph.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jhoan
 */
import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private String employeeId;
    private LocalDate date;
    private LocalTime timeIn;
    private LocalTime timeOut;

    public Attendance(String employeeId, LocalDate date, LocalTime timeIn, LocalTime timeOut) {
        this.employeeId = employeeId;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public double getWorkedHours() {
        return java.time.Duration.between(timeIn, timeOut).toMinutes() / 60.0;
    }

    public String getDateString() {
        return date.toString();
    }
}
