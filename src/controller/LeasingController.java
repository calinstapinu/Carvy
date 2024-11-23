package controller;

import model.Car;
import model.Client;
import model.Leasing;
import service.LeasingService;
import service.LeasingManager;

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


    public void createLeasing(long leasingId, Car car, Client client, int durationMonths, float interestRate) {
        try {
            Leasing leasing = new Leasing(leasingId, car, client, durationMonths, interestRate);
            leasingService.addLeasing(leasing);
            System.out.println("The Leasing Contract was successfully created.");
        } catch (Exception e) {
            System.err.println("There is an error when creating the contract: " + e.getMessage());
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
            System.out.println("The Leasing Contract associated with " + client.getFullName() + ":");
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
    public void calculateTotalAmount(long leasingId) {
        try {
            Leasing leasing = leasingService.findLeasingById(leasingId);
            float totalAmount = leasingService.calculateTotalAmount(leasing);
            System.out.println("The total sum of the Leasing Contract with the ID " + leasingId + " is: " + totalAmount);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    public float calculateMonthlyRate(int durationMonths, float carPrice) {
        return leasingService.calculateMonthlyRate(durationMonths, carPrice);
    }
}
