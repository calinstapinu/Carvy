package org.dealership.repository.entityRepos;

import org.dealership.model.Leasing;
import org.dealership.model.Client;
import org.dealership.repository.FileRepository;
import org.dealership.repository.parsers.LeasingParser;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Repository for managing {@link Leasing} entities.
 * Provides additional methods specific to leasing operations.
 */
public class LeasingRepository extends FileRepository<Leasing> {
    public LeasingRepository(File file, LeasingParser parser) {
        super(file, parser);
    }
    /**
     * Finds all leasing contracts associated with a specific client.
     *
     * @param client the {@link Client} for whom leasing contracts are to be retrieved.
     * @return a list of {@link Leasing} contracts for the given client.
     */
    public List<Leasing> findByClient(Client client) {
        System.out.println("muie");

        readAll().stream()
                .filter(leasing -> leasing.getClient().getId() == client.getId())
                .collect(Collectors.toList()).stream().forEach(leasing -> {
                    System.out.println(leasing);
                });

        return readAll().stream()
                .filter(leasing -> leasing.getClient().getId() == client.getId())
                .collect(Collectors.toList());
    }
}
