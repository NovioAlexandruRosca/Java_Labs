package org.example.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * An employee class contains the capabilites associated with an employee
 */
public class Employee {

    List<String> capabilities;

    private final Integer id;
    static Integer employeeId = 0;


    public Employee(){
        capabilities = new ArrayList<>();
        employeeId++;
        this.id = employeeId;
    }

    public Integer getId() {
        return id;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void addCapabilities(String capability) {
        this.capabilities.add(capability);
    }
}
