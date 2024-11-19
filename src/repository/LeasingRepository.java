package repository;

import model.Leasing;
import model.Client;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Repository for managing {@link Leasing} entities.
 * Provides additional methods specific to leasing operations.
 */
public class LeasingRepository extends InMemoryRepository<Leasing> {

    /**
     * Finds all leasing contracts associated with a specific client.
     *
     * @param client the {@link Client} for whom leasing contracts are to be retrieved.
     * @return a list of {@link Leasing} contracts for the given client.
     */
    public List<Leasing> findByClient(Client client) {
        return readAll().stream()
                .filter(leasing -> leasing.getClient().equals(client))
                .collect(Collectors.toList());
    }
}