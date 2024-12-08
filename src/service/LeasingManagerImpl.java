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
    public float calculateTotalAmount(float monthlyRate, int durationMonths, float adminFee, float taxRate) {
        float baseAmount = monthlyRate * durationMonths;
        float totalBeforeTax = baseAmount + adminFee;
        return totalBeforeTax + (totalBeforeTax * (taxRate / 100));
    }


    @Override
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

        float principal = carPrice - downPayment;
        float monthlyInterestRate = annualInterestRate / 100 / 12;

        float numerator = principal * monthlyInterestRate * (float) Math.pow(1 + monthlyInterestRate, durationMonths);
        float denominator = (float) (Math.pow(1 + monthlyInterestRate, durationMonths) - 1);

        return numerator / denominator;
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