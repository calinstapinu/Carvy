package org.dealership.repository.parsers;

import org.dealership.model.Leasing;
import org.dealership.model.Car;
import org.dealership.model.Client;

import java.text.ParseException;

/**
 * Parser for {@link Leasing} entities to/from CSV format.
 */
public class LeasingParser implements EntityParser<Leasing> {
    private final EntityParser<Car> carParser;
    private final EntityParser<Client> clientParser;


    /**
     * Constructs a new {@link LeasingParser} with the specified parsers for {@link Car} and {@link Client}.
     *
     * @param carParser    the parser for {@link Car} objects
     * @param clientParser the parser for {@link Client} objects
     */
    public LeasingParser(EntityParser<Car> carParser, EntityParser<Client> clientParser) {
        this.carParser = carParser;
        this.clientParser = clientParser;
    }

    /**
     * Converts a {@link Leasing} object into a CSV string representation.
     *
     * @param leasing the {@link Leasing} object to convert
     * @return a CSV string representation of the leasing
     */
    @Override
    public String toCSV(Leasing leasing) {
        return leasing.getId() + "," +
                leasing.getCarId() + "," + // Serialize carId directly
                leasing.getClientId() + "," + // Serialize clientId directly
                leasing.getDurationMonths() + "," +
                leasing.getInterestRate() + "," +
                leasing.getMonthlyRate() + "," +
                leasing.getTotalAmount();
    }


    /**
     * Parses a CSV string to create a {@link Leasing} object.
     *
     * @param csv the CSV string representing a leasing
     * @return a {@link Leasing} object created from the CSV string
     */
//    @Override
//    public Leasing fromCSV(String csv) {
//        String[] fields = csv.split(",", 5); // Adjust the delimiter and expected count
//        if (fields.length < 5) {
//            throw new IllegalArgumentException("Malformed CSV input: " + csv);
//        }
//        Car car = carParser.fromCSV(fields[1]); // Parse the car data
//        Client client = clientParser.fromCSV(fields[2]); // Parse the client data
//
//        return new Leasing(
//                Long.parseLong(fields[0]), // Leasing ID
//                car, // Car object
//                client, // Client object
//                Integer.parseInt(fields[3]), // Duration in months
//                Float.parseFloat(fields[4])  // Interest rate
//        );
//    }


    @Override
    public Leasing fromCSV(String csv) {
        String[] fields = csv.split(",", 7); // Adjust to the exact number of fields in your CSV
        if (fields.length < 7) {
            throw new IllegalArgumentException("Malformed CSV input: " + csv);
        }
        try {
            long leasingId = Long.parseLong(fields[0]);
            long carId = Long.parseLong(fields[1]); // Store only the car ID
            long clientId = Long.parseLong(fields[2]); // Store only the client ID
            int durationMonths = Integer.parseInt(fields[3]);
            float interestRate = Float.parseFloat(fields[4]);
            float monthlyRate = Float.parseFloat(fields[5]);
            float totalAmount = Float.parseFloat(fields[6]);

            return new Leasing(leasingId, carId, clientId, durationMonths, interestRate, monthlyRate, totalAmount);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Failed to parse leasing CSV input: " + csv, e);
        }
    }


    @Override
    public long getId(Leasing leasing) {
        return leasing.getId();
    }
}