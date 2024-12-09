package org.dealership.controller;

import org.dealership.exceptions.BusinessLogicException;
import org.dealership.exceptions.DatabaseException;
import org.dealership.exceptions.EntityNotFoundException;
import org.dealership.exceptions.ValidationException;

import org.dealership.model.Client;
import org.dealership.repository.DBRepository;
import org.dealership.service.ClientService;

import java.util.List;

/**
 * Controller class for managing {@link Client} entities.
 * Provides methods for adding, retrieving, listing, and deleting clients.
 */
public class ClientController {
    private final ClientService clientService;
    private final DBRepository<Client> dbClientRepo;

    public ClientController(ClientService clientService, DBRepository<Client> dbClientRepo) {
        this.clientService = clientService;
        this.dbClientRepo = dbClientRepo;
    }

    // Adaugă un client nou
    public void addClient(String firstName, String lastName, String cnp, Long clientId) {
        Client client = new Client(firstName, lastName, cnp, clientId);
        clientService.addClient(client);
        System.out.println("The Client has been added successfully.");
    }

    public void addClientToDB(String firstName, String lastName, String cnp, Long clientId) {
        try {
            Client client = new Client(firstName, lastName, cnp, clientId);
            dbClientRepo.create(client);
            System.out.println("Client added successfully to the database!");
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error adding client to the database: " + e.getMessage());
        }
    }

    /**
     * Lists all clients currently in the system.
     * Each client's details are printed to the console.
     */
    public void listAllClients() {
        List<Client> clients = clientService.getAllClients();
        System.out.println("The list with all clients: ");
        clients.forEach(System.out::println);
    }

    public void listAllClientsFromDB() {
        dbClientRepo.readAll().forEach(System.out::println);
    }

    /**
     * Finds a client by their unique ID and displays their details.
     *
     * @param clientId the ID of the client to find
     */
    public void findClientById(long clientId) {
        try {
            Client client = clientService.findClientById(clientId);
            System.out.println("Client found:");
            System.out.println(client);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error finding client by ID: " + e.getMessage());
        }
    }

    /**
     * Finds a client by their name and displays their details.
     *
     * @param name the name of the client to find
     */
    public void findClientByName(String name) {
        try {
            Client client = clientService.findClientByName(name);
            System.out.println("Client found:");
            System.out.println(client);
        } catch (ValidationException | EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error finding client by name: " + e.getMessage());
        }
    }

    /**
     * Deletes a client by their unique ID.
     *
     * @param clientId the ID of the client to delete
     */
    public void deleteClient(long clientId) {
        try {
            clientService.deleteClient(clientId);
            System.out.println("The Client with ID " + clientId + " has been deleted successfully.");
        }catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Client with ID " + clientId + " not found.");
        } catch (Exception e) {
            throw new DatabaseException("Error deleting client: " + e.getMessage());
        }
    }

    public void deleteClientFromDB(long clientId) {
        try {
            dbClientRepo.delete(clientId);
            System.out.println("Client deleted successfully from the database.");
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Client with ID " + clientId + " not found in the database.");
        } catch (Exception e) {
            throw new DatabaseException("Error deleting client from the database: " + e.getMessage());
        }
    }
}
