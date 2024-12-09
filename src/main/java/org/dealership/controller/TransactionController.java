package org.dealership.controller;

import org.dealership.model.Car;
import org.dealership.model.Employee;
import org.dealership.model.Transaction;
import org.dealership.model.enums.TransactionType;
import org.dealership.repository.DBRepository;
import org.dealership.service.TransactionService;

import java.util.List;

/**
 * Controller class for managing {@link Transaction} entities.
 * Provides methods for adding transactions, listing all transactions,
 * and filtering transactions by type.
 */
public class TransactionController {
    private final TransactionService transactionService;
    private final DBRepository<Transaction> dbTransactionRepo;

    public TransactionController(TransactionService transactionService, DBRepository<Transaction> dbTransactionRepo) {
        this.transactionService = transactionService;
        this.dbTransactionRepo = dbTransactionRepo;
    }

    public void addTransaction(Transaction transaction) {
        try {
            transactionService.addTransaction(transaction);
            System.out.println("The transaction was successfully added.");
        } catch (Exception e) {
            System.err.println("Error when adding the transaction: " + e.getMessage());
        }
    }

    public void addTransactionToDB(long transactionId, long carId, long clientId, TransactionType transactionType) {
        try {
            Transaction transaction = new Transaction(transactionId, carId, clientId, transactionType, new java.util.Date());
            dbTransactionRepo.create(transaction);
            System.out.println("Transaction added successfully to the database!");
        } catch (Exception e) {
            System.err.println("Error adding transaction to the database: " + e.getMessage());
        }
    }

    public void listAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        System.out.println("The list of all transactions:");
        transactions.forEach(System.out::println);
    }


    public void listAllTransactionsFromDB() {
        dbTransactionRepo.readAll().forEach(System.out::println);
    }

    /**
     * Lists all transactions of a specific type.
     * Each transaction's details are printed to the console.
     *
     * @param type the {@link TransactionType} to filter by
     */
    public void listTransactionsByType(TransactionType type) {
        try {
            List<Transaction> transactions = transactionService.findTransactionsByType(type);
            System.out.println("The transactions labeled as " + type + ":");
            transactions.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
