package org.dealership.model;

import java.util.ArrayList;
import java.util.List;

public class Employee extends Person {
    private long employeeId;
    private String role; // Example: Manager, Salesperson, etc.
    private List<Car> managedCars = new ArrayList<>();

    public Employee(String firstName, String lastName, String CNP, long employeeId, String role) {
        super(firstName, lastName, CNP);
        this.employeeId = employeeId;
        this.role = role;
    }

    public long getId() {
        return employeeId;
    }

    public void setId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Car> getManagedCars() {
        return managedCars;
    }

    public void assignCar(Car car) {
        managedCars.add(car);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", role='" + role + '\'' +
                ", managedCars=" + managedCars +
                ", fullName=" + getFullName() +
                '}';
    }
}
