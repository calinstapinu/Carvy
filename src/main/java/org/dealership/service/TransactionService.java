package org.dealership.service;

import org.dealership.model.Transaction;
import org.dealership.model.enums.TransactionType;
import org.dealership.repository.DBRepository;
import org.dealership.repository.entityRepos.TransactionRepository;

import java.util.List;

/**
 * Service class for managing {@link Transaction} entities.
 * Provides business logic for adding transactions, retrieving them by type,
 * and retrieving all transactions.
 */
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final DBRepository<Transaction> dbTransactionRepo;

    public TransactionService(TransactionRepository transactionRepository, DBRepository<Transaction> dbTransactionRepo) {
        this.transactionRepository = transactionRepository;
        this.dbTransactionRepo = dbTransactionRepo;
    }

    public void addTransaction(Transaction transaction) {
        transactionRepository.create(transaction);
    }

    public List<Transaction> findTransactionsByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.readAll();
    }
}