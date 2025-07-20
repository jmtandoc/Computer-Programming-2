package com.motorph.ui;


import com.motorph.database.Database;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jhoan
 */
public class Main {
    public static void main(String[] args) {
        Database.loadFromExcel("src/main/resources/MotorPH Employee Data.xlsx");
        new LoginUI();
    }
}
