package controller;

import model.Car;
import model.Employee;
import service.EmployeeService;

import java.util.List;

/**
 * Controller class for managing {@link Employee} entities.
 * Provides methods for adding employees, listing all employees,
 * retrieving employees by ID, assigning cars to employees, and deleting employees.
 */
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void addEmployee(String firstName, String lastName, String cnp, Long employeeId, String role) {
        Employee employee = new Employee(firstName, lastName, cnp, employeeId, role);
        employeeService.addEmployee(employee);
        System.out.println("Employee added successfully.");
    }

    public void listAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        System.out.println("List of employees:");
        employees.forEach(System.out::println);
    }

    public void findEmployeeById(long employeeId) {
        try {
            Employee employee = employeeService.findEmployeeById(employeeId);
            System.out.println("Employee found:");
            System.out.println(employee);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void assignCarToEmployee(long employeeId, Car car) {
        try {
            employeeService.assignCarToEmployee(employeeId, car);
            System.out.println("The Car was successfully attributed to the Employee with ID " + employeeId + ".");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void deleteEmployee(long employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            System.out.println("The Employee with ID " + employeeId + " has been successfully deleted.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
