package service;

import model.Client;
import repository.ClientRepository;

import java.util.List;

/**
 * Service class for managing {@link Client} entities.
 * Provides business logic for operations such as adding, retrieving,
 * and deleting clients.
 */
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void addClient(Client client) {
        clientRepository.create(client);
    }

    /**
     * Finds a client by their unique ID.
     *
     * @param clientId the ID of the client to find
     * @return the {@link Client} with the specified ID
     * @throws IllegalArgumentException if the client does not exist
     */
    public Client findClientById(long clientId) {
        Client client = clientRepository.read(clientId);
        if (client == null) {
            throw new IllegalArgumentException("The Client with ID " + clientId + " does not exist.");
        }
        return client;
    }

    /**
     * Finds a client by their name.
     *
     * @param name the name of the client to find
     * @return the {@link Client} with the specified name
     * @throws IllegalArgumentException if the client does not exist
     */
    public Client findClientByName(String name) {
        Client client = clientRepository.findByName(name);
        if (client == null) {
            throw new IllegalArgumentException("The Client with the name " + name + " does not exist.");
        }
        return client;
    }


    public List<Client> getAllClients() {
        return clientRepository.readAll();
    }


    public void deleteClient(long clientId) {
        Client client = findClientById(clientId);
        clientRepository.delete(client.getId());
    }
}