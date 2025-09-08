package com.shoppingcart.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shoppingcart.app.entities.Client;
import com.shoppingcart.app.repositories.ClientRepository;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testGetAllClients() {
    when(clientRepository.findAll()).thenReturn(List.of(new Client(), new Client()));

    List<Client> clients = clientService.getAllClients();

    assertNotNull(clients);
    assertEquals(2, clients.size());
}

    @Test
    void testGetClientById() {
        Long clientId = 1L;
        Client mockClient = new Client();
        mockClient.setClientId(clientId);
        mockClient.setName("John Doe");

        when(clientRepository.findById(clientId)).thenReturn(java.util.Optional.of(mockClient));

        Client client = clientService.getClientById(clientId);

        assertNotNull(client);
        assertEquals("John Doe", client.getName());
    }

    @Test
    void testCreateClient() {
        Client newClient = new Client();
        newClient.setName("Jane Doe");
        when(passwordEncoder.encode(any())).thenReturn("encryptedPassword");
        when(clientRepository.save(newClient)).thenReturn(newClient);

        Client createdClient = clientService.createClient(newClient);

        assertNotNull(createdClient);
        assertEquals("Jane Doe", createdClient.getName());
    }

    @Test
    void testUpdateClient() {
        Client existingClient = new Client();
        existingClient.setClientId(1L);
        existingClient.setName("John Smith");

        when(clientRepository.save(existingClient)).thenReturn(existingClient);

        Client updatedClient = clientService.updateClient(existingClient);

        assertNotNull(updatedClient);
        assertEquals("John Smith", updatedClient.getName());
    }

    @Test
    void testDeleteClient() {
        Long clientId = 1L;

        boolean isDeleted = clientService.deleteClient(clientId);

        assertEquals(true, isDeleted);
    }
}