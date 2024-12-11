package org.dealership.model;

import java.io.Serializable;

/**
 * Represents a leasing contract for a car.
 * Contains details about the car, client, leasing duration, interest rate, monthly rate, and total amount.
 */

public class Leasing implements HasID{
    private long leasingId;
    private Car car;
    private Client client;
    private long carId;
    private long clientId;
    private int durationMonths;
    private float monthlyRate;
    private float interestRate;
    private float totalAmount;

    // Default constructor
    public Leasing() {
    }
    /**
     * Constructs a new {@link Leasing} object.
     *
     * @param leasingId
     * @param car
     * @param client
     * @param durationMonths
     * @param interestRate
     */
    public Leasing(long leasingId, Car car, Client client, int durationMonths, float interestRate) {
        this.leasingId = leasingId;
        this.car = car;
        this.client = client;
        this.durationMonths = durationMonths;
        this.interestRate = interestRate;
        this.monthlyRate = calculateMonthlyRate();
        this.totalAmount = calculateTotalAmount();
    }

    // Constructor with carId and clientId
    public Leasing(Long leasingId, Long carId, Long clientId, int durationMonths, float interestRate) {
        this.leasingId = leasingId;
        this.carId = carId;
        this.clientId = clientId;
        this.durationMonths = durationMonths;
        this.interestRate = interestRate;
        this.monthlyRate = 0;
        this.totalAmount = 0;
    }

    public Leasing(long leasingId, Car car, Client client, int durationMonths, float monthlyRate, float interestRate, float totalAmount) {
        this.leasingId = leasingId;
        this.car = car;
        this.client = client;
        this.durationMonths = durationMonths;
        this.monthlyRate = monthlyRate;
        this.interestRate = interestRate;
        this.totalAmount = totalAmount;
    }

    public long getId() {
        return leasingId;
    }

    public void setId(long leasingId) {
        this.leasingId = leasingId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public int getDurationMonths() {
        return durationMonths;
    }

    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }

    public float getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(float monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    private float calculateMonthlyRate() {
        if (car != null) {
            return (car.getPrice() * (1 + interestRate)) / durationMonths;
        }
        throw new IllegalStateException("Car is not initialized for calculating monthly rate");
    }

    private float calculateTotalAmount() {
        return calculateMonthlyRate() * durationMonths;
    }

    public void finalizeLeasing() {
        car.markAsLeased();
    }

    @Override
    public String toString() {
        return "Leasing{" +
                "leasingId=" + leasingId +
                ", car=" + car +
                ", client=" + client +
                ", durationMonths=" + durationMonths +
                ", monthlyRate=" + monthlyRate +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
