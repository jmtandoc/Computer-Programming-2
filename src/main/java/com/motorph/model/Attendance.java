package com.motorph.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private LocalDate date;
    private LocalTime timeIn;
    private LocalTime timeOut;

    public Attendance(LocalDate date, LocalTime timeIn, LocalTime timeOut) {
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public LocalDate getDate() { return date; }
    public LocalTime getTimeIn() { return timeIn; }
    public LocalTime getTimeOut() { return timeOut; }
}
