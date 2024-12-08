package org.dealership.model;

import org.dealership.model.enums.TransactionType;

import java.util.Date;

public class Transaction {
    private long transactionId;
    private Car car;
    private Client client;
    private TransactionType transactionType; // Sold or Leased
    private Date transactionDate;

    public Transaction(long transactionId, Car car, Client client, TransactionType transactionType, Date transactionDate) {
        this.transactionId = transactionId;
        this.car = car;
        this.client = client;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
    }

    public long getId() {
        return transactionId;
    }

    public void setId(long transactionId) {
        this.transactionId = transactionId;
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
                ", car=" + car +
                ", client=" + client +
                ", transactionType=" + transactionType +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
