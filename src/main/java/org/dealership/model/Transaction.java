package org.dealership.model;

import org.dealership.model.enums.TransactionType;

import java.util.Date;

public class Transaction implements HasID{
    private long transactionId;
    private long carId;
    private long clientId;
    private TransactionType transactionType; // Sold or Leased
    private Date transactionDate;

    // Default constructor
    public Transaction() {
    }

    public Transaction(long transactionId, Long carId, Long clientId, TransactionType transactionType, Date transactionDate) {
        this.transactionId = transactionId;
        this.carId = carId;
        this.clientId = clientId;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
    }

    public long getId() {
        return transactionId;
    }

    public void setId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getCar() {
        return carId;
    }

    public void setCar(long car) {
        this.carId = car;
    }

    public long getClient() {
        return clientId;
    }

    public void setClient(long client) {
        this.clientId = client;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", car=" + carId +
                ", client=" + clientId +
                ", transactionType=" + transactionType +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
