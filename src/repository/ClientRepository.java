package repository;

import model.Client;
import repository.parsers.ClientParser;

import java.io.File;

/**
 * Repository for managing {@link Client} entities.
 * Provides additional methods specific to client operations.
 */
public class ClientRepository extends FileRepository<Client> {
    public ClientRepository(File file, ClientParser parser) {
        super(file, parser);
    }
    /**
     * Finds a client by their full name.
     *
     * @param name the full name of the client.
     * @return the {@link Client} with the given name, or {@code null} if not found.
     */
    public Client findByName(String name) {
        return readAll().stream()
                .filter(client -> client.getFullName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
