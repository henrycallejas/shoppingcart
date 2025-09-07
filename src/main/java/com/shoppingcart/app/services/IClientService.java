package com.shoppingcart.app.services;

import java.util.List;

import com.shoppingcart.app.model.Client;

public interface IClientService {
    Client getClientById(Long id);
    List<Client> getAllClients();
    Client createClient(Client client);
    Client updateClient(Client client);
    boolean deleteClient(Long id);
}
