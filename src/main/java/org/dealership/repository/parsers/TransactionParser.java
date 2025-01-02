package org.dealership.repository.parsers;

import org.dealership.model.Transaction;
import org.dealership.model.Car;
import org.dealership.model.Client;
import org.dealership.model.enums.TransactionType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parser for {@link Transaction} entities to/from CSV format.
 */
public class TransactionParser implements EntityParser<Transaction> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final EntityParser<Car> carParser;
    private final EntityParser<Client> clientParser;

    public TransactionParser(EntityParser<Car> carParser, EntityParser<Client> clientParser) {
        this.carParser = carParser;
        this.clientParser = clientParser;
    }

    @Override
    public String toCSV(Transaction transaction) {
        return transaction.getId() + "," +
                transaction.getCar() + "," +
                transaction.getClient() + "," +
                transaction.getTransactionType() + "," +
                DATE_FORMAT.format(transaction.getTransactionDate());
    }

    @Override
    public Transaction fromCSV(String csv) {
        String[] fields = csv.split(",", 5); // Ensure we split into exactly 5 fields
        if (fields.length < 5) {
            throw new IllegalArgumentException("Malformed CSV input: " + csv);
        }
        try {
            long transactionId = Long.parseLong(fields[0]);
            long carId = Long.parseLong(fields[1]); // Treat as ID
            long clientId = Long.parseLong(fields[2]); // Treat as ID
            TransactionType type = TransactionType.valueOf(fields[3]);
            Date date = DATE_FORMAT.parse(fields[4]);

            // Return a Transaction object using the IDs
            return new Transaction(transactionId, carId, clientId, type, date);
        } catch (ParseException | IllegalArgumentException e) {
            throw new RuntimeException("Failed to parse transaction CSV input: " + csv, e);
        }
    }


    @Override
    public long getId(Transaction transaction) {
        return transaction.getId();
    }
}