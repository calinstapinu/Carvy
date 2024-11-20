package controller;

import model.Client;
import service.ClientService;

import java.util.List;

/**
 * Controller class for managing {@link Client} entities.
 * Provides methods for adding, retrieving, listing, and deleting clients.
 */
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Adaugă un client nou
    public void addClient(String firstName, String lastName, String cnp) {
        Client client = new Client(firstName, lastName, cnp, 0);
        clientService.addClient(client);
        System.out.println("The Client has been added successfully.");
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
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
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
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
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
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
