package org.dealership.repository.entityRepos;

import org.dealership.model.Employee;
import org.dealership.repository.FileRepository;
import org.dealership.repository.parsers.EmployeeParser;

import java.io.File;

/**
 * Repository for managing {@link Employee} entities.
 * Provides a base implementation with potential for employee-specific extensions.
 */
public class EmployeeRepository extends FileRepository<Employee> {
    public EmployeeRepository(File file, EmployeeParser parser) {
        super(file, parser);
    }
}
