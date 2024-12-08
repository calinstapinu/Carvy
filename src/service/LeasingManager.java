package service;

import model.Client;
import model.Leasing;

/**
 * Interface for managing leasing-related calculations.
 * Provides methods for calculating total amounts, monthly rates,
 * and adjusting rates based on client credit history.
 */
public interface LeasingManager {
    float calculateMonthlyRate(float carPrice, int durationMonths, float annualInterestRate, float downPayment);
    float calculateTotalAmount(float monthlyRate, int durationMonths, float adminFee, float taxRate);
    float adjustRateBasedOnCredit(Client client, float baseRate);

}