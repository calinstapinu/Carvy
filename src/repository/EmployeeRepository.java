package repository;

import model.Employee;

/**
 * Repository for managing {@link Employee} entities.
 * Provides a base implementation with potential for employee-specific extensions.
 */
public class EmployeeRepository extends InMemoryRepository<Employee> {

}