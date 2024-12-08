package org.dealership.repository;

import org.dealership.model.*;
import org.dealership.model.enums.CarStatus;
import org.dealership.model.enums.TransactionType;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TransactionDBRepositoryTest {
    private DBRepository<Transaction> transactionRepo;
    private DBRepository<Car> carRepo;
    private DBRepository<Client> clientRepo;

    @Before
    public void setUp() {
        transactionRepo = new DBRepository<>(Transaction.class, "transactions");
        carRepo = new DBRepository<>(Car.class, "cars");
        clientRepo = new DBRepository<>(Client.class, "clients");
    }

    @Test
    public void testCreateTransaction() {
        // Create and save a Car
        Car car = new Car(69L, "BMW", "X5", 2022, 50000.0f, 5000, CarStatus.SOLD);
        carRepo.create(car);

        // Create and save a Client
        Client client = new Client("Bob", "Miller", "9627712", 73L);
        clientRepo.create(client);

        // Create and save a Transaction with carId and clientId
        Transaction transaction = new Transaction(5L, car.getId(), client.getId(), TransactionType.SOLD, new Date());
        transactionRepo.create(transaction);

        // Fetch and assert the Transaction
        Transaction fetchedTransaction = transactionRepo.read(5L);
        assertNotNull(fetchedTransaction);
        assertEquals(TransactionType.SOLD, fetchedTransaction.getTransactionType());
        assertEquals(car.getId(), fetchedTransaction.getCar());
        assertEquals(client.getId(), fetchedTransaction.getClient());
    }

    @Test
    public void testReadTransaction() {
        // Create and save a Car
        Car car = new Car(65L, "Audi", "Q7", 2023, 60000.0f, 4000, CarStatus.AVAILABLE);
        carRepo.create(car);

        // Create and save a Client
        Client client = new Client("Alice", "Smith", "8721456321", 59L);
        clientRepo.create(client);

        // Create and save a Transaction
        Transaction transaction = new Transaction(3L, car.getId(), client.getId(), TransactionType.LEASED, new Date());
        transactionRepo.create(transaction);

        // Fetch and assert the Transaction
        Transaction fetchedTransaction = transactionRepo.read(3L);
        assertNotNull(fetchedTransaction);
        assertEquals(TransactionType.LEASED, fetchedTransaction.getTransactionType());
        assertEquals(car.getId(), fetchedTransaction.getCar());
        assertEquals(client.getId(), fetchedTransaction.getClient());
    }
}
