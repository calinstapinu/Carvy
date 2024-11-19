package service;

import model.Client;
import model.Leasing;
import repository.LeasingRepository;

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
     * @param leasingRepository the repository to manage leasing data
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
    public void addLeasing(Leasing leasing) {
        // Calculate the monthly rate and total amount using the LeasingManager
        float monthlyRate = leasingManager.calculateMonthlyRate(
                leasing.getDurationMonths(),
                leasing.getCar().getPrice()
        );
        leasing.setMonthlyRate(monthlyRate);

        float totalAmount = leasingManager.calculateTotalAmount(leasing);
        leasing.setTotalAmount(totalAmount);

        // Save the leasing contract
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
     * Calculates the total amount for a leasing contract.
     * Delegates the calculation to the {@link LeasingManager}.
     *
     * @param leasing the {@link Leasing} contract for which the total amount is calculated
     * @return the total amount for the specified leasing contract
     */
    public float calculateTotalAmount(Leasing leasing) {
        return leasingManager.calculateTotalAmount(leasing);
    }
}