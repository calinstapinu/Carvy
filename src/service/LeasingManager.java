package service;

import model.Client;
import model.Leasing;

/**
 * Interface for managing leasing-related calculations.
 * Provides methods for calculating total amounts, monthly rates,
 * and adjusting rates based on client credit history.
 */
public interface LeasingManager {

    float calculateTotalAmount(Leasing leasing);

    float calculateMonthlyRate(int durationMonths, float monthlyRate);

    float adjustRateBasedOnCredit(Client client, float baseRate);
}