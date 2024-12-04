package repository.parsers;

import model.Employee;

/**
 * Parser for {@link Employee} entities to/from CSV format.
 */
public class EmployeeParser implements EntityParser<Employee> {

    /**
     * Converts an {@link Employee} object into a CSV string representation.
     *
     * @param employee the {@link Employee} object to convert
     * @return a CSV string representation of the employee
     */
    @Override
    public String toCSV(Employee employee) {
        return employee.getId() + "," +
                employee.getFirstName() + "," +
                employee.getLastName() + "," +
                employee.getCNP() + "," +
                employee.getRole();
    }

    /**
     * Parses a CSV string to create an {@link Employee} object.
     *
     * @param csv the CSV string representing an employee
     */
    @Override
    public Employee fromCSV(String csv) {
        String[] fields = csv.split(",");
        return new Employee(
                fields[1],
                fields[2],
                fields[3],
                Long.parseLong(fields[0]),
                fields[4]
        );
    }

    @Override
    public long getId(Employee employee) {
        return employee.getId();
    }
}