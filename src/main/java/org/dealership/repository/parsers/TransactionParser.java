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
                carParser.toCSV(transaction.getCar()) + "," +
                clientParser.toCSV(transaction.getClient()) + "," +
                transaction.getTransactionType() + "," +
                DATE_FORMAT.format(transaction.getTransactionDate());
    }

    @Override
    public Transaction fromCSV(String csv) {
        String[] fields = csv.split(",", 5);
        Car car = carParser.fromCSV(fields[1]);
        Client client = clientParser.fromCSV(fields[2]);
        try {
            Date date = DATE_FORMAT.parse(fields[4]);
            return new Transaction(
                    Long.parseLong(fields[0]),
                    car,
                    client,
                    TransactionType.valueOf(fields[3]),
                    date
            );
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse transaction date.", e);
        }
    }

    @Override
    public long getId(Transaction transaction) {
        return transaction.getId();
    }
}