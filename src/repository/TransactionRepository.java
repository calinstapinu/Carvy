package repository;

import model.Transaction;
import model.enums.TransactionType;
import repository.parsers.TransactionParser;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Repository for managing {@link Transaction} entities.
 * Provides additional methods specific to transaction operations.
 */
public class TransactionRepository extends FileRepository<Transaction> {
    public TransactionRepository(File file, TransactionParser parser) {
        super(file, parser);
    }
    /**
     * Finds all transactions of a specific type.
     *
     * @param type the {@link TransactionType} to filter by.
     * @return a list of {@link Transaction} entities with the specified type.
     */
    public List<Transaction> findByType(TransactionType type) {
        return readAll().stream()
                .filter(transaction -> transaction.getTransactionType() == type)
                .collect(Collectors.toList());
    }
}
