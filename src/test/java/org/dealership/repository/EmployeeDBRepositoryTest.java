package org.dealership.repository;

import org.dealership.model.Employee;
import org.dealership.model.enums.CarStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EmployeeDBRepositoryTest {

    private DBRepository<Employee> employeeRepository;

    @Before
    public void setup() {
        employeeRepository = new DBRepository<>(Employee.class, "employees");
    }

    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee("Johnn", "Doee", "122345", 2L, "Manager");
        employeeRepository.create(employee);

        Employee retrievedEmployee = employeeRepository.read(2);
        assertNotNull(retrievedEmployee);
        assertEquals("Johnn", retrievedEmployee.getFirstName());
        assertEquals("Manager", retrievedEmployee.getRole());
    }

    @Test
    public void testReadEmployee() {
        Employee employee = new Employee("Jane", "Smith", "987654321", 3, "Salesperson");
        employeeRepository.create(employee);

        Employee retrievedEmployee = employeeRepository.read(3);
        assertNotNull(retrievedEmployee);
        assertEquals("Jane", retrievedEmployee.getFirstName());
        assertEquals("Salesperson", retrievedEmployee.getRole());
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee("Mark", "Taylor", "56789023", 5, "Manager");
        employeeRepository.create(employee);

        // Update role
        employee.setRole("Manager");
        employeeRepository.update(employee);

        Employee updatedEmployee = employeeRepository.read(4);
        assertNotNull(updatedEmployee);
        assertEquals("Manager", updatedEmployee.getRole());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee("Alice", "Brown", "456789012", 7, "Assistant");
        employeeRepository.create(employee);

        employeeRepository.delete(7);
        Employee deletedEmployee = employeeRepository.read(7);
        assertNull(deletedEmployee);
    }

    @Test
    public void testGetAllEmployees() {
        Employee employee1 = new Employee("Mike", "Johnson", "111111111", 7, "Manager");
        Employee employee2 = new Employee("Sara", "Davis", "222222222", 8, "Salesperson");
        employeeRepository.create(employee1);
        employeeRepository.create(employee2);

        List<Employee> employees = employeeRepository.readAll();
        assertTrue(employees.size() >= 2);
    }
}
