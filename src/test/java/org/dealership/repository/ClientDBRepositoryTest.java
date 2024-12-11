package org.dealership.repository;

import org.dealership.model.Client;
import org.dealership.model.Car;
import org.dealership.model.Leasing;
import org.dealership.model.enums.CarStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClientDBRepositoryTest {

    private DBRepository<Client> clientRepository;

    @Before
    public void setup() {
        clientRepository = new DBRepository<>(Client.class, "clients");
    }

    @Test
    public void testCreateClient() {
        Client client = new Client("Johnn", "Doee", "1347585", 14);
        clientRepository.create(client);

        Client retrievedClient = clientRepository.read(14);
        assertNotNull(retrievedClient);
        assertEquals("Johnn", retrievedClient.getFirstName());
        assertEquals("Doee", retrievedClient.getLastName());
        assertEquals("1347585", retrievedClient.getCNP());
    }

    @Test
    public void testReadClient() {
        Client client = new Client("Janeyyyyy", "Smithhhhhh", "987654326", 12L);
        clientRepository.create(client);

        Client retrievedClient = clientRepository.read(12);
        assertNotNull(retrievedClient);
        assertEquals("Janeyyyyy", retrievedClient.getFirstName());
        assertEquals("Smithhhhhh", retrievedClient.getLastName());
    }

    @Test
    public void testUpdateClient() {
        Client client = new Client("Alice", "Brown", "12233455", 22L);
        clientRepository.create(client);

        client.setFirstName("Alicio");
        clientRepository.update(client);

        Client updatedClient = clientRepository.read(22);
        assertNotNull(updatedClient);
        assertEquals("Alicio", updatedClient.getFirstName());
    }

    @Test
    public void testDeleteClient() {
        Client client = new Client("Bob", "Green", "9988776655", 44L);
        clientRepository.create(client);

        clientRepository.delete(44);
        Client deletedClient = clientRepository.read(44);
        assertNull(deletedClient);
    }
}
