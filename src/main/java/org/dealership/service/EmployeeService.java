package org.dealership.service;

import org.dealership.model.Car;
import org.dealership.model.Client;
import org.dealership.model.Employee;
import org.dealership.repository.DBRepository;
import org.dealership.repository.entityRepos.EmployeeRepository;

import java.util.List;

/**
 * Service class for managing {@link Employee} entities.
 * Provides business logic for operations such as adding, retrieving,
 * deleting employees, and assigning cars to employees.
 */
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DBRepository<Employee> dbEmployeeRepo;

    public EmployeeService(EmployeeRepository employeeRepository, DBRepository<Employee> dbEmployeeRepo) {
        this.employeeRepository = employeeRepository;
        this.dbEmployeeRepo = dbEmployeeRepo;
    }

    public void addEmployee(Employee employee) {
        employeeRepository.create(employee);
    }


    /**
     * Finds an employee by their unique ID.
     *
     * @param employeeId the ID of the employee to find
     * @return the {@link Employee} with the specified ID
     * @throws IllegalArgumentException if the employee does not exist
     */
    public Employee findEmployeeById(long employeeId) {
        Employee employee = employeeRepository.read(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("The Employee with ID " + employeeId + " does not exist.");
        }
        return employee;
    }


    public List<Employee> getAllEmployees() {
        return employeeRepository.readAll();
    }


    /**
     * Assigns a car to an employee by updating their car list.
     *
     * @param employeeId the ID of the employee to assign the car to
     * @param car        the {@link Car} to assign
     * @throws IllegalArgumentException if the employee does not exist
     */
    public void assignCarToEmployee(long employeeId, Car car) {
        Employee employee = findEmployeeById(employeeId);
        employee.assignCar(car);
        employeeRepository.update(employee);
    }

    public void deleteEmployee(long employeeId) {
        Employee employee = findEmployeeById(employeeId);
        employeeRepository.delete(employee.getId());
    }
}