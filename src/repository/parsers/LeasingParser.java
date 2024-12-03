package repository.parsers;

import model.Leasing;
import model.Car;
import model.Client;

/**
 * Parser for {@link Leasing} entities to/from CSV format.
 */
public class LeasingParser implements EntityParser<Leasing> {
    private final EntityParser<Car> carParser;
    private final EntityParser<Client> clientParser;



    public LeasingParser(EntityParser<Car> carParser, EntityParser<Client> clientParser) {
        this.carParser = carParser;
        this.clientParser = clientParser;
    }

    @Override
    public String toCSV(Leasing leasing) {
        return leasing.getId() + "," +
                carParser.toCSV(leasing.getCar()) + "," +
                clientParser.toCSV(leasing.getClient()) + "," +
                leasing.getDurationMonths() + "," +
                leasing.getInterestRate() + "," +
                leasing.getMonthlyRate() + "," +
                leasing.getTotalAmount();
    }

    @Override
    public Leasing fromCSV(String csv) {
        String[] fields = csv.split(",", 5); // Adjust the delimiter and expected count
        if (fields.length < 5) {
            throw new IllegalArgumentException("Malformed CSV input: " + csv);
        }
        Car car = carParser.fromCSV(fields[1]); // Parse the car data
        Client client = clientParser.fromCSV(fields[2]); // Parse the client data

        return new Leasing(
                Long.parseLong(fields[0]), // Leasing ID
                car, // Car object
                client, // Client object
                Integer.parseInt(fields[3]), // Duration in months
                Float.parseFloat(fields[4])  // Interest rate
        );
    }





    @Override
    public long getId(Leasing leasing) {
        return leasing.getId();
    }
}