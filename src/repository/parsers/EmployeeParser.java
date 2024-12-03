package repository.parsers;

import model.Employee;

/**
 * Parser for {@link Employee} entities to/from CSV format.
 */
public class EmployeeParser implements EntityParser<Employee> {

    @Override
    public String toCSV(Employee employee) {
        return employee.getId() + "," +
                employee.getFirstName() + "," +
                employee.getLastName() + "," +
                employee.getCNP() + "," +
                employee.getRole();
    }

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