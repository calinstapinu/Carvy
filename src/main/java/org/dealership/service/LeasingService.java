package org.dealership.service;

import org.dealership.model.Client;
import org.dealership.model.Leasing;
import org.dealership.repository.entityRepos.LeasingRepository;

import java.util.List;

/**
 * Service class for managing {@link Leasing} contracts.
 * This class provides business logic for adding, retrieving, and managing leasing contracts,
 * leveraging the {@link LeasingManager} for calculations.
 */
public class LeasingService {
    private final LeasingRepository leasingRepository;
    private final LeasingManager leasingManager;


    /**
     * Constructs a new {@code LeasingService} with the specified repository and manager.
     *
     * @param leasingRepository the repository to manage leasing repository.data
     * @param leasingManager    the manager responsible for leasing calculations
     */
    public LeasingService(LeasingRepository leasingRepository, LeasingManager leasingManager, LeasingManagerImpl leasingManagerImpl) {
        this.leasingRepository = leasingRepository;
        this.leasingManager = leasingManager;
    }


    /**
     * Adds a new leasing contract to the system.
     * This method calculates the monthly rate and total amount using the {@link LeasingManager}.
     *
     * @param leasing the {@link Leasing} contract to add
     */
    public void addLeasing(Leasing leasing, float downPayment, float adminFee, float taxRate) {
        float monthlyRate = leasingManager.calculateMonthlyRate(
                leasing.getCar().getPrice(),
                leasing.getDurationMonths(),
                leasing.getInterestRate(),
                downPayment
        );
        leasing.setMonthlyRate(monthlyRate);

        float totalAmount = leasingManager.calculateTotalAmount(monthlyRate, leasing.getDurationMonths(), adminFee, taxRate);
        leasing.setTotalAmount(totalAmount);

        leasingRepository.create(leasing);
    }


    /**
     * Finds a leasing contract by its unique ID.
     *
     * @param leasingId the ID of the leasing contract to find
     * @return the {@link Leasing} contract with the specified ID
     * @throws IllegalArgumentException if the leasing contract does not exist
     */
    public Leasing findLeasingById(long leasingId) {
        Leasing leasing = leasingRepository.read(leasingId);
        if (leasing == null) {
            throw new IllegalArgumentException("The Leasing Contract with ID " + leasingId + " does not exist.");
        }
        return leasing;
    }


    public List<Leasing> findLeasingsByClient(Client client) {
        return leasingRepository.findByClient(client);
    }


    /**
     * Adjusts the monthly rate for a leasing contract based on the client's credit history.
     *
     * @param client   the {@link Client} whose credit history is used for adjustment
     * @param baseRate the base rate to adjust
     * @return the adjusted rate
     */
    public float adjustRate(Client client, float baseRate) {
        return leasingManager.adjustRateBasedOnCredit(client, baseRate);
    }

    /**
     * Calculates the total amount for a leasing contract, including fees and taxes.
     *
     * @param monthlyRate    the calculated monthly rate
     * @param durationMonths the duration of the leasing contract in months
     * @param adminFee       a one-time administrative fee
     * @param taxRate        the tax rate as a percentage (e.g., 8 for 8%)
     * @return the total amount for the leasing contract
     */
    public float calculateTotalAmount(float monthlyRate, int durationMonths, float adminFee, float taxRate) {
        if (durationMonths <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0 months.");
        }
        if (taxRate < 0) {
            throw new IllegalArgumentException("Tax rate cannot be negative.");
        }

        // Total base payment
        float baseAmount = monthlyRate * durationMonths;

        // Add administrative fees
        float totalBeforeTax = baseAmount + adminFee;

        // Apply taxes
        float totalWithTax = totalBeforeTax + (totalBeforeTax * (taxRate / 100));

        return totalWithTax;
    }

    /**
     * Calculates the monthly leasing rate based on the car price, contract duration, and interest rate.
     *
     * @param durationMonths the duration of the leasing contract in months
     * @param carPrice       the price of the car
     * @return the calculated monthly rate
     */
    public float calculateMonthlyRate(float carPrice, int durationMonths, float annualInterestRate, float downPayment) {
        if (durationMonths <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0 months.");
        }
        if (carPrice <= 0 || carPrice <= downPayment) {
            throw new IllegalArgumentException("Car price must be greater than 0 and higher than the down payment.");
        }
        if (annualInterestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }

        // Adjust the principal amount by subtracting the down payment
        float principal = carPrice - downPayment;

        // Convert annual interest rate to a monthly interest rate (as a decimal)
        float monthlyInterestRate = annualInterestRate / 100 / 12;

        // Apply the formula for monthly payments with interest
        float numerator = principal * monthlyInterestRate * (float) Math.pow(1 + monthlyInterestRate, durationMonths);
        float denominator = (float) (Math.pow(1 + monthlyInterestRate, durationMonths) - 1);

        return numerator / denominator; // Monthly payment
    }
}