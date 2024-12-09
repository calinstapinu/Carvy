package org.dealership.repository;

import org.dealership.model.Car;
import org.dealership.model.Client;
import org.dealership.model.Leasing;
import org.dealership.model.enums.CarStatus;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LeasingDBRepositoryTest {
    private DBRepository<Leasing> leasingRepo;
    private DBRepository<Car> carRepo;
    private DBRepository<Client> clientRepo;

    @Before
    public void setUp() {
        leasingRepo = new DBRepository<>(Leasing.class, "leasings");
        carRepo = new DBRepository<>(Car.class, "cars");
        clientRepo = new DBRepository<>(Client.class, "clients");
    }

    /**
     * Test creating a new leasing entry.
     */
    @Test
    public void testCreateLeasing() {
        // Step 1: Create a Car object
        Car car = new Car(7L, "Toyota", "Camry", 2022, 25000.0f, 12000, CarStatus.AVAILABLE);
        System.out.println("Creating Car: " + car);
        carRepo.create(car);

        // Step 2: Create a Client object
        Client client = new Client("John", "Doe", "679", 6L);
        System.out.println("Creating Client: " + client);
        clientRepo.create(client);

        // Step 3: Create a Leasing object
        Leasing leasing = new Leasing(6L, 7L, 6L, 36, 0.05f);
        System.out.println("Creating Leasing: " + leasing);
        leasingRepo.create(leasing);

        // Step 4: Read back the Leasing object to verify
        System.out.println("Fetching Leasing with ID: 1");
        Leasing fetchedLeasing = leasingRepo.read(6);

        // Debug output of the fetched Leasing
        System.out.println("Fetched Leasing: " + fetchedLeasing);

        // Step 5: Assertions to verify the fetched Leasing is not null
        assertNotNull("Fetched Leasing is null", fetchedLeasing);
        assertEquals("Leasing ID mismatch", 6L, fetchedLeasing.getId());
        assertEquals("Duration months mismatch", 36, fetchedLeasing.getDurationMonths());
        assertEquals("Interest rate mismatch", 0.05f, fetchedLeasing.getInterestRate(), 0.001);
    }




    /**
     * Test reading an existing leasing entry.
     */
    @Test
    public void testReadLeasing() {
        Car car = new Car(2L, "BMW", "X5", 2022, 50000.0f, 5000, CarStatus.AVAILABLE);
        Client client = new Client("Alice", "Smith", "9876543210", 2L);
        carRepo.create(car);
        clientRepo.create(client);

        Leasing leasing = new Leasing(2L, car.getId(), client.getId(), 36, 0.04f);
        leasingRepo.create(leasing);

        Leasing fetchedLeasing = leasingRepo.read(2L);
//        assertNotNull(fetchedLeasing);
        assertEquals((Long) car.getId(), fetchedLeasing.getCarId());
        assertEquals((Long) client.getId(), fetchedLeasing.getClientId());
    }

    /**
     * Test updating an existing leasing entry.
     */
    @Test
    public void testUpdateLeasing() {
        Car car = new Car(3L, "Audi", "A4", 2021, 40000.0f, 10000, CarStatus.AVAILABLE);
        Client client = new Client("Bob", "Brown", "5678901234", 3L);
        carRepo.create(car);
        clientRepo.create(client);

        Leasing leasing = new Leasing(3L, car.getId(), client.getId(), 12, 0.05f);
        leasingRepo.create(leasing);

        leasing.setDurationMonths(24);
        leasing.setInterestRate(0.04f);
        leasingRepo.update(leasing);

        Leasing updatedLeasing = leasingRepo.read(3L);
        assertNotNull(updatedLeasing);
        assertEquals(24, updatedLeasing.getDurationMonths());
        assertEquals(0.04f, updatedLeasing.getInterestRate(), 0.001f);
    }

    /**
     * Test deleting a leasing entry.
     */
    @Test
    public void testDeleteLeasing() {
        Car car = new Car(4L, "Mercedes", "C-Class", 2020, 35000.0f, 15000, CarStatus.AVAILABLE);
        Client client = new Client("Jane", "Doe", "3456789012", 4L);
        carRepo.create(car);
        clientRepo.create(client);

        Leasing leasing = new Leasing(4L, car.getId(), client.getId(), 18, 0.06f);
        leasingRepo.create(leasing);

        leasingRepo.delete(4L);
        Leasing deletedLeasing = leasingRepo.read(4L);
        assertNull(deletedLeasing);
    }

    /**
     * Test creating a leasing with invalid data (e.g., negative duration).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateLeasingWithInvalidData() {
        Car car = new Car(5L, "Toyota", "Corolla", 2019, 20000.0f, 30000, CarStatus.AVAILABLE);
        Client client = new Client("Sam", "Wilson", "1112233445", 5L);
        carRepo.create(car);
        clientRepo.create(client);

        // Negative duration should throw an exception
        Leasing leasing = new Leasing(5L, car.getId(), client.getId(), -12, 0.05f);
        leasingRepo.create(leasing);
    }
}
