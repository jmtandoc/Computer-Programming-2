package com.motorph.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jhoan
 */
import java.util.*;

public class SupervisorMap {
    private Map<String, List<String>> supervisorToEmployees = new HashMap<>();

    // Assign employee to supervisor
    public void assign(String supervisorId, String employeeId) {
        supervisorToEmployees
            .computeIfAbsent(supervisorId, k -> new ArrayList<>())
            .add(employeeId);
    }

    // Get employees under a supervisor
    public List<String> getEmployees(String supervisorId) {
        return supervisorToEmployees.getOrDefault(supervisorId, Collections.emptyList());
    }

    // Optional: Remove or reassign methods if needed
}
