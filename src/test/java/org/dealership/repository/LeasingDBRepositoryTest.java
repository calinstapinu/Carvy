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
        // Create and insert a car
        Car car = new Car(153L, "BMW", "X5", 2022, 50000.0f, 5000, CarStatus.AVAILABLE);
        carRepo.create(car);

        // Fetch the car with the generated ID
        Car fetchedCar = carRepo.read(154L); // Adjust the ID accordingly based on auto-increment
        assertNotNull(fetchedCar);

        // Create and insert a client
        Client client = new Client("Alice", "Smith", "123490", 153L);
        clientRepo.create(client);

        // Fetch the client with the generated ID
        Client fetchedClient = clientRepo.read(154L); // Adjust the ID accordingly based on auto-increment

        // Create the leasing using the fetched car and client
        Leasing leasing = new Leasing(153L, fetchedCar.getId(), fetchedClient.getId(), 12, 0.05f);
        leasingRepo.create(leasing);

        // Verify the leasing was created successfully
        Leasing fetchedLeasing = leasingRepo.read(153L);
        assertNotNull(fetchedLeasing);
        assertEquals((Long)fetchedCar.getId(), fetchedLeasing.getCarId());
        assertEquals((Long)fetchedClient.getId(), fetchedLeasing.getClientId());
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
