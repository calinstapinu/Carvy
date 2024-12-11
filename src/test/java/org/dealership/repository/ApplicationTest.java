package org.dealership.repository;

import org.dealership.model.*;
import org.dealership.model.enums.CarStatus;
import org.dealership.model.enums.TransactionType;
import org.dealership.repository.entityRepos.CarRepository;
import org.dealership.repository.entityRepos.LeasingRepository;
import org.dealership.repository.parsers.CarParser;
import org.dealership.repository.parsers.ClientParser;
import org.dealership.repository.parsers.LeasingParser;
import org.junit.Before;
import org.junit.Test;
import org.dealership.controller.CarController;
import org.dealership.service.CarService;
import org.dealership.repository.parsers.CarParser;
import org.dealership.service.*;
import org.dealership.controller.*;


import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ApplicationTest {

    private DBRepository<Car> dbcarRepository;
    private DBRepository<Client> clientRepository;
    private DBRepository<Employee> employeeRepository;
    private DBRepository<Transaction> transactionRepository;
    private DBRepository<Leasing> leasingRepository;


    private LeasingService leasingService;
    private LeasingService mockedLeasingService;




    @Before
    public void setUp() {
        dbcarRepository = new DBRepository<>(Car.class, "cars");
        clientRepository = new DBRepository<>(Client.class, "clients");
        employeeRepository = new DBRepository<>(Employee.class, "employees");
        transactionRepository = new DBRepository<>(Transaction.class, "transactions");
        leasingRepository = new DBRepository<>(Leasing.class, "leasing");

        File leasingFile = new File("data/leasings.txt");
        LeasingRepository fileleasingRepo = new LeasingRepository(leasingFile, new LeasingParser(new CarParser(), new ClientParser()));

        LeasingManager leasingManager = new LeasingManagerImpl();
        LeasingManagerImpl leasingManagerImpl = new LeasingManagerImpl();
        leasingService = new LeasingService(fileleasingRepo, leasingManager, leasingManagerImpl, leasingRepository);
    }

    // CRUD FOR CAR
    @Test
    public void testCreateCar() {
        Car car = new Car(17, "Toyota", "Camry", 2022, 25000, 12000, CarStatus.AVAILABLE);
        dbcarRepository.create(car);

        Car retrievedCar = dbcarRepository.read(7);
        assertEquals("Toyota", retrievedCar.getBrand());
    }


    @Test
    public void testDeleteCar() {
        dbcarRepository.delete(13);
        Car deletedCar = dbcarRepository.read(13);
        assertNull(deletedCar);
    }

    @Test
    public void testGetAllCars() {
        List<Car> cars = dbcarRepository.readAll();
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
        dbcarRepository.create(car);

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
        dbcarRepository.create(car);

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
        dbcarRepository.create(car);
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
        dbcarRepository.create(car);
        clientRepository.create(client);

        Leasing leasing = new Leasing(4L, car.getId(), client.getId(), 18, 0.06f);
        leasingRepository.create(leasing);

        leasingRepository.delete(4L);
        Leasing deletedLeasing = leasingRepository.read(4L);
        assertNull(deletedLeasing);
    }


    @Test
    public void testCreateLeasingWithIncorrectDuration() {
        Car car = new Car(169L, "Ford", "Focus", 2021, 18000.0f, 10000, CarStatus.AVAILABLE);
        dbcarRepository.create(car);
        Client client = new Client("John", "Doe", "555156", 169L);
        clientRepository.create(client);

        // 0 luni
        Leasing leasing = new Leasing(169L, car.getId(), client.getId(), 0, 0.04f);
        leasingRepository.create(leasing);

        Leasing retrievedLeasing = leasingRepository.read(169L);

        System.out.println("Leasing creat: " + retrievedLeasing);
        assertNotNull("Leasing-ul nu ar trebui să fie null", retrievedLeasing);
        assertEquals("Durata leasing-ului ar trebui să fie 0 luni", 0, retrievedLeasing.getDurationMonths());
    }


    @Test
    public void testCreateLeasingWithIncorrectInterestRate() {
        Car car = new Car(9L, "Mazda", "CX-5", 2021, 27000.0f, 15000, CarStatus.AVAILABLE);
        dbcarRepository.create(car);

        Client client = new Client("Jane", "Doe", "555987654", 9L);
        clientRepository.create(client);

        // Introducem un interest_rate negativ, ceea ce nu ar trebui să fie valid
        Leasing leasing = new Leasing(9L, car.getId(), client.getId(), 24, -0.05f);
        leasingRepository.create(leasing);

        Leasing retrievedLeasing = leasingRepository.read(9L);

        System.out.println("Leasing creat cu interest_rate negativ: " + retrievedLeasing);

        assertNotNull("Leasing-ul nu ar trebui să fie null", retrievedLeasing);
        assertEquals("Interest rate ar trebui să fie negativ", -0.05f, retrievedLeasing.getInterestRate(), 0.001);
    }

    // Test pentru un caz valid
    @Test
    public void testCalculateMonthlyRateValid() {
        float carPrice = 20000.0f;
        int durationMonths = 36;
        float annualInterestRate = 5.0f;
        float downPayment = 5000.0f;

        float monthlyRate = leasingService.calculateMonthlyRate(carPrice, durationMonths, annualInterestRate, downPayment);

        assertEquals("Rata lunară calculată nu este corectă", 449.55795f, monthlyRate, 0.01f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateMonthlyRateInvalidInterestRate() {
        float carPrice = 20000.0f;
        int durationMonths = 36;
        float annualInterestRate = -5.0f; //  negativă
        float downPayment = 5000.0f;

        // arunca exceptie
        leasingService.calculateMonthlyRate(carPrice, durationMonths, annualInterestRate, downPayment);
    }

}
