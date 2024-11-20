package service;

import model.Client;
import model.Leasing;

/**
 * Implementation of the {@link LeasingManager} interface.
 * Provides methods for calculating total amounts, monthly rates,
 * and adjusting rates based on client credit history.
 */
public class LeasingManagerImpl implements LeasingManager {

    @Override
    public float calculateTotalAmount(Leasing leasing) {
        return leasing.getMonthlyRate() * leasing.getDurationMonths();
    }

    @Override
    public float calculateMonthlyRate(int durationMonths, float monthlyRate) {
        return monthlyRate / durationMonths;
    }

    /**
     * Adjusts the base rate for a leasing contract based on the client's credit history.
     * Loyal clients (those with more than 2 purchased cars) receive a 10% discount on the rate.
     *
     * @param client   the {@link Client} whose credit history is used for adjustment
     * @param baseRate the initial base rate
     * @return the adjusted rate, with a 10% discount for loyal clients
     */
    @Override
    public float adjustRateBasedOnCredit(Client client, float baseRate) {
        if (client.getPurchasedCars().size() > 2) {
            return baseRate * 0.9f; // Discont for loyal clients
        } else {
            return baseRate;
        }
    }
}