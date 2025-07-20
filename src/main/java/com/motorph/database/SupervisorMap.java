package com.motorph.model;

import java.util.*;

public class SupervisorMap {
    private Map<String, List<String>> supervisorToEmps = new HashMap<>();

    public void assign(String supervisorId, String employeeId) {
        supervisorToEmps.computeIfAbsent(supervisorId, k -> new ArrayList<>()).add(employeeId);
    }

    public List<String> getEmployees(String supervisorId) {
        return supervisorToEmps.getOrDefault(supervisorId, new ArrayList<>());
    }

    public Map<String, List<String>> getAllMappings() {
        return supervisorToEmps;
    }
}
