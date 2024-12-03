package repository.parsers;

import model.Client;

/**
 * Parser for {@link Client} entities to/from CSV format.
 */
public class ClientParser implements EntityParser<Client> {

    @Override
    public String toCSV(Client client) {
        return client.getId() + "," +
                client.getFirstName() + "," +
                client.getLastName() + "," +
                client.getCNP();
    }

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