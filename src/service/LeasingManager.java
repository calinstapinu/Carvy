package service;

import model.Client;
import model.Leasing;

public interface LeasingManager {
    // Calculează suma totală pentru un contract de leasing
    float calculateTotalAmount(Leasing leasing);

    // Calculează rata lunară pentru un contract de leasing
    float calculateMonthlyRate(int durationMonths, float monthlyRate);


    // Ajustează rata lunară pe baza istoricului creditului clientului
    float adjustRateBasedOnCredit(Client client, float baseRate);
}
