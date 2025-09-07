package com.shoppingcart.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.app.entities.Client;
import com.shoppingcart.app.repositories.ClientRepository;

@Service
public class ClientService implements IClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client getClientById(Long id) {
       return clientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public boolean deleteClient(Long id) {
        clientRepository.deleteById(id);
        return true;
    }
    
}
