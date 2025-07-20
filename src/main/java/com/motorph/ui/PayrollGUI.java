package com.motorph.ui;

import com.motorph.database.Database;
import javax.swing.*;

public class PayrollGUI {
    public static void main(String[] args) {
        // 🧠 Load data from Excel
        Database.loadFromExcel("src/MotorPH Employee Data.xlsx");

        // 🔐 Launch Login UI
        new LoginUI();  // Or replace with another dashboard directly if testing
    }
}
