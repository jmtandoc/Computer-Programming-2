package com.motorph.util;

import com.motorph.model.AttendanceLog;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AttendanceExporter {

    public static void exportAttendance(String empId, String month, List<AttendanceLog> logs) {
        String filename = "Attendance_" + empId + "_" + month + ".txt";

        try (FileWriter fw = new FileWriter(filename)) {
            fw.write("Attendance Logs for " + empId + " — " + month + "\n\n");

            for (AttendanceLog log : logs) {
                fw.write("Date: " + log.getDate() + "\n");
                fw.write("Time In: " + log.getTimeIn() + ", Time Out: " + log.getTimeOut() + "\n\n");
            }

            fw.flush();
            System.out.println("✅ Attendance exported to " + filename);
        } catch (IOException e) {
            System.err.println("❌ Error exporting attendance: " + e.getMessage());
        }
    }
}
