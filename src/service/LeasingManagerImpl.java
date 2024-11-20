package service;

import model.Client;
import model.Leasing;

public class LeasingManagerImpl implements LeasingManager {

    @Override
    public float calculateTotalAmount(Leasing leasing) {
        return leasing.getMonthlyRate() * leasing.getDurationMonths();
    }

    @Override
    public float calculateMonthlyRate(int durationMonths, float monthlyRate) {
        return monthlyRate / durationMonths;
    }

    @Override
    public float adjustRateBasedOnCredit(Client client, float baseRate) {
        // Exemplu simplu: Reducem rata pentru clienții buni
        // (Poți integra o metodă mai complexă bazată pe istoricul de credit al clientului)
        if (client.getPurchasedCars().size() > 2) {
            return baseRate * 0.9f; // Reducere de 10% pentru clienți fideli
        } else {
            return baseRate;
        }
    }
}
