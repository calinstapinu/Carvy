package org.dealership.controller;

import org.dealership.exceptions.BusinessLogicException;
import org.dealership.exceptions.DatabaseException;
import org.dealership.exceptions.EntityNotFoundException;
import org.dealership.exceptions.ValidationException;

import org.dealership.model.Car;
import org.dealership.model.Client;
import org.dealership.model.Employee;
import org.dealership.model.Leasing;
import org.dealership.repository.DBRepository;
import org.dealership.service.LeasingService;
import org.dealership.service.LeasingManager;

import java.util.List;

/**
 * Controller class for managing {@link Leasing} contracts.
 * Provides methods for creating leasing contracts, listing contracts by client,
 * retrieving contracts by ID, and calculating the total amount for a leasing contract.
 */
public class LeasingController {
    private final LeasingService leasingService;
    private final DBRepository<Leasing> dbLeasingRepo;

    public LeasingController(LeasingService leasingService, DBRepository<Leasing> dbLeasingRepo) {
        this.leasingService = leasingService;
        this.dbLeasingRepo = dbLeasingRepo;
    }


    public void createLeasing(long leasingId, Car car, Client client, int durationMonths, float interestRate, float downPayment, float adminFee, float taxRate) {
        try {
            Leasing leasing = new Leasing(leasingId, car, client, durationMonths, interestRate);
            leasingService.addLeasing(leasing, downPayment, adminFee, taxRate);
            System.out.println("The Leasing Contract was successfully created.");
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error creating the leasing contract: " + e.getMessage());
        }
    }

    /**
     * Adds a new leasing contract directly to the database with the specified details.
     *
     * @param leasingId      the ID of the leasing contract
     * @param car            the car associated with the leasing
     * @param client         the client associated with the leasing
     * @param durationMonths the duration of the leasing in months
     * @param interestRate   the interest rate
     * @param monthlyRate    the calculated monthly rate
     * @param totalAmount    the calculated total amount
     */
    public void addLeasingToDB(long leasingId, Car car, Client client, int durationMonths, float interestRate, float monthlyRate, float totalAmount) {
        try {
            Leasing leasing = new Leasing(leasingId, car, client, durationMonths, monthlyRate, interestRate, totalAmount);
            dbLeasingRepo.create(leasing);
            System.out.println("Leasing contract added successfully to the database!");
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error adding leasing to the database: " + e.getMessage());
        }
    }

    /**
     * Lists all leasing contracts associated with a specific client.
     *
     * @param client the {@link Client} whose leasing contracts are to be retrieved
     */
    public void listLeasingsByClient(Client client) {
        try {
            List<Leasing> leasings = leasingService.findLeasingsByClient(client);
            System.out.println("The Leasing Contracts associated with " + client.getFullName() + ":");
            leasings.forEach(System.out::println);
        } catch (ValidationException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error retrieving leasing contracts for client: " + e.getMessage());
        }
    }

    /**
     * Finds a leasing contract by its unique ID and displays its details.
     *
     * @param leasingId the ID of the leasing contract to retrieve
     */
    public void findLeasingById(long leasingId) {
        try {
            Leasing leasing = leasingService.findLeasingById(leasingId);
            System.out.println("Leasing Contract found:");
            System.out.println(leasing);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error finding leasing contract by ID: " + e.getMessage());
        }
    }


    public void listAllLeasings() {
        List<Leasing> leasings = leasingService.getAllLeasings();
        System.out.println("List of employees:");
        leasings.forEach(System.out::println);
    }

    public void listAllLeasingsFromDB() {
        dbLeasingRepo.readAll().forEach(System.out::println);
    }

    /**
     * Calculates and displays the total amount for a specific leasing contract.
     *
     * @param leasingId the ID of the leasing contract for which the total amount is calculated
     */
    public float calculateTotalAmount(long leasingId, float adminFee, float taxRate)
    {
        try {
            // Retrieve the leasing contract by ID
            Leasing leasing = leasingService.findLeasingById(leasingId);

            // Calculate the total amount using the leasing service
            float totalAmount = leasingService.calculateTotalAmount(
                    leasing.getMonthlyRate(),
                    leasing.getDurationMonths(),
                    adminFee,
                    taxRate
            );

            // Return the calculated total amount
            return totalAmount;

        }  catch (ValidationException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error calculating total amount: " + e.getMessage());
        }
    }
}
