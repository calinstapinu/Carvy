package org.dealership.controller;

import org.dealership.model.Car;
import org.dealership.model.Client;
import org.dealership.model.Leasing;
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

    public LeasingController(LeasingService leasingService) {
        this.leasingService = leasingService;
    }


    public void createLeasing(long leasingId, Car car, Client client, int durationMonths, float interestRate, float downPayment, float adminFee, float taxRate) {
        try {
            Leasing leasing = new Leasing(leasingId, car, client, durationMonths, interestRate);
            leasingService.addLeasing(leasing, downPayment, adminFee, taxRate);
            System.out.println("The Leasing Contract was successfully created.");
        } catch (Exception e) {
            System.err.println("There was an error creating the contract: " + e.getMessage());
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
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
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
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
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

        } catch (Exception e) {
            // Handle errors and return 0 in case of failure
            System.err.println("Error calculating total amount: " + e.getMessage());
            return 0;
        }
    }
}
