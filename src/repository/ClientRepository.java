package repository;

import model.Client;

/**
 * Repository for managing {@link Client} entities.
 * Provides additional methods specific to client operations.
 */
public class ClientRepository extends InMemoryRepository<Client> {

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
