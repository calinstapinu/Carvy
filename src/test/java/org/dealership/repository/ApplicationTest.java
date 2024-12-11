package org.dealership.repository;

import org.dealership.model.*;
import org.dealership.model.enums.CarStatus;
import org.dealership.model.enums.TransactionType;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ApplicationTest {

    private DBRepository<Car> carRepository;
    private DBRepository<Client> clientRepository;
    private DBRepository<Employee> employeeRepository;
    private DBRepository<Transaction> transactionRepository;
    private DBRepository<Leasing> leasingRepository;

    @Before
    public void setUp() {
        carRepository = new DBRepository<>(Car.class, "cars");
        clientRepository = new DBRepository<>(Client.class, "clients");
        employeeRepository = new DBRepository<>(Employee.class, "employees");
        transactionRepository = new DBRepository<>(Transaction.class, "transactions");
        leasingRepository = new DBRepository<>(Leasing.class, "leasing");
    }

    // CRUD FOR CAR
    @Test
    public void testCreateCar() {
        Car car = new Car(17, "Toyota", "Camry", 2022, 25000, 12000, CarStatus.AVAILABLE);
        carRepository.create(car);

        Car retrievedCar = carRepository.read(7);
        assertEquals("Toyota", retrievedCar.getBrand());
    }


    @Test
    public void testDeleteCar() {
        carRepository.delete(13);
        Car deletedCar = carRepository.read(13);
        assertNull(deletedCar);
    }

    @Test
    public void testGetAllCars() {
        List<Car> cars = carRepository.readAll();
        assertNotNull(cars);
        assertFalse(cars.isEmpty());

        for (Car car : cars) {
            System.out.println(car);
        }
    }

    // CRUD FOR CLIENT
    @Test
    public void testCreateClient() {
        Client client = new Client("Johnn", "Doee", "1347585", 14);
        clientRepository.create(client);

        Client retrievedClient = clientRepository.read(14);
        assertNotNull(retrievedClient);
        assertEquals("Johnn", retrievedClient.getFirstName());
    }

    @Test
    public void testReadClient() {
        Client client = new Client("Janeyyyyy", "Smithhhhhh", "987654326", 12L);
        clientRepository.create(client);

        Client retrievedClient = clientRepository.read(12);
        assertNotNull(retrievedClient);
        assertEquals("Janeyyyyy", retrievedClient.getFirstName());
    }

    @Test
    public void testUpdateClient() {
        Client client = new Client("Alice", "Brown", "12233455", 22L);
        clientRepository.create(client);

        client.setFirstName("Alicio");
        clientRepository.update(client);

        Client updatedClient = clientRepository.read(22);
        assertNotNull(updatedClient);
        assertEquals("Alicio", updatedClient.getFirstName());
    }

    @Test
    public void testDeleteClient() {
        Client client = new Client("Bob", "Green", "9988776655", 44L);
        clientRepository.create(client);

        clientRepository.delete(44);
        Client deletedClient = clientRepository.read(44);
        assertNull(deletedClient);
    }

    // CRUD FOR EMPLOYEE
    @Test
    public void testCreateEmployee() {
        Employee employee = new Employee("Johnn", "Doee", "122345", 2L, "Manager");
        employeeRepository.create(employee);

        Employee retrievedEmployee = employeeRepository.read(2);
        assertNotNull(retrievedEmployee);
        assertEquals("Johnn", retrievedEmployee.getFirstName());
    }

    @Test
    public void testReadEmployee() {
        Employee employee = new Employee("Jane", "Smith", "987654321", 3, "Salesperson");
        employeeRepository.create(employee);

        Employee retrievedEmployee = employeeRepository.read(3);
        assertNotNull(retrievedEmployee);
        assertEquals("Jane", retrievedEmployee.getFirstName());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee("Alice", "Brown", "456789012", 7, "Assistant");
        employeeRepository.create(employee);

        employeeRepository.delete(7);
        Employee deletedEmployee = employeeRepository.read(7);
        assertNull(deletedEmployee);
    }

    // CRUD FOR TRANSACTION
    @Test
    public void testCreateTransaction() {
        Car car = new Car(69L, "BMW", "X5", 2022, 50000.0f, 5000, CarStatus.SOLD);
        carRepository.create(car);

        Client client = new Client("Bob", "Miller", "9627712", 73L);
        clientRepository.create(client);

        Transaction transaction = new Transaction(5L, car.getId(), client.getId(), TransactionType.SOLD, new Date());
        transactionRepository.create(transaction);

        Transaction fetchedTransaction = transactionRepository.read(5L);
        assertNotNull(fetchedTransaction);
        assertEquals(TransactionType.SOLD, fetchedTransaction.getTransactionType());
    }

    @Test
    public void testCreateLeasing() {
        Car car = new Car(7L, "Toyota", "Camry", 2022, 25000.0f, 12000, CarStatus.AVAILABLE);
        System.out.println("Creating Car: " + car);
        carRepository.create(car);

        Client client = new Client("John", "Doe", "679", 6L);
        System.out.println("Creating Client: " + client);
        clientRepository.create(client);

        Leasing leasing = new Leasing(6L, 7L, 6L, 36, 0.05f);
        System.out.println("Creating Leasing: " + leasing);
        leasingRepository.create(leasing);

        System.out.println("Fetching Leasing with ID: 1");
        Leasing fetchedLeasing = leasingRepository.read(6);

        System.out.println("Fetched Leasing: " + fetchedLeasing);

        assertNotNull("Fetched Leasing is null", fetchedLeasing);
        assertEquals("Leasing ID mismatch", 6L, fetchedLeasing.getId());
        assertEquals("Duration months mismatch", 36, fetchedLeasing.getDurationMonths());
        assertEquals("Interest rate mismatch", 0.05f, fetchedLeasing.getInterestRate(), 0.001);
    }

    @Test
    public void testReadLeasing() {
        Car car = new Car(2L, "BMW", "X5", 2022, 50000.0f, 5000, CarStatus.AVAILABLE);
        Client client = new Client("Alice", "Smith", "9876543210", 2L);
        carRepository.create(car);
        clientRepository.create(client);

        Leasing leasing = new Leasing(2L, car.getId(), client.getId(), 36, 0.04f);
        leasingRepository.create(leasing);

        Leasing fetchedLeasing = leasingRepository.read(2L);
//        assertNotNull(fetchedLeasing);
        assertEquals((Long) car.getId(), fetchedLeasing.getCarId());
        assertEquals((Long) client.getId(), fetchedLeasing.getClientId());
    }

    @Test
    public void testDeleteLeasing() {
        Car car = new Car(4L, "Mercedes", "C-Class", 2020, 35000.0f, 15000, CarStatus.AVAILABLE);
        Client client = new Client("Jane", "Doe", "3456789012", 4L);
        carRepository.create(car);
        clientRepository.create(client);

        Leasing leasing = new Leasing(4L, car.getId(), client.getId(), 18, 0.06f);
        leasingRepository.create(leasing);

        leasingRepository.delete(4L);
        Leasing deletedLeasing = leasingRepository.read(4L);
        assertNull(deletedLeasing);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateLeasingWithInvalidData() {
        Car car = new Car(5L, "Toyota", "Corolla", 2019, 20000.0f, 30000, CarStatus.AVAILABLE);
        Client client = new Client("Sam", "Wilson", "1112233445", 5L);
        carRepository.create(car);
        clientRepository.create(client);

        // Negative duration should throw an exception
        Leasing leasing = new Leasing(5L, car.getId(), client.getId(), -12, 0.05f);
        leasingRepository.create(leasing);
    }

}
