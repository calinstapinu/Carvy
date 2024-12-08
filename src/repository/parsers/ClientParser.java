package repository.parsers;

import model.Client;

/**
 * Parser for {@link Client} entities to/from CSV format.
 */
public class ClientParser implements EntityParser<Client> {

    /**
     * Converts a {@link Client} object into a CSV string representation.
     *
     * @param client the {@link Client} object to convert
     * @return a CSV string representation of the client
     */
    @Override
    public String toCSV(Client client) {
        return client.getId() + "," +
                client.getFirstName() + "," +
                client.getLastName() + "," +
                client.getCNP();
    }

    /**
     * Parses a CSV string to create a {@link Client} object.
     *
     * @param csv the CSV string representing a client
     */
    @Override
    public Client fromCSV(String csv) {
        String[] fields = csv.split(",");
        return new Client(
                fields[1], // firstName
                fields[2], // lastName
                fields[3], // CNP
                Long.parseLong(fields[0]) // clientId
        );
    }
    @Override
    public long getId(Client client) {
        return client.getId();
    }
}