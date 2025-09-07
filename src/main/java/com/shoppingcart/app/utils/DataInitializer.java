package com.shoppingcart.app.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.shoppingcart.app.entities.Client;
import com.shoppingcart.app.repositories.ClientRepository;
import com.shoppingcart.app.repositories.ProductRepository;

@Component
public class DataInitializer implements CommandLineRunner{
    private final ClientRepository clientRepository;

    public DataInitializer(ClientRepository clientRepository, ProductRepository productRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
public void run(String... args) throws Exception {
    if (!clientRepository.findByUsername("admin").isPresent()) {
        Client client = Client.builder()
                .address("Soyapango, San Salvador")
                .email("prueba@gmail.com")
                .name("Henry")
                .lastName("Callejas")
                .username("admin")
                .password("$2a$12$tM3cexnIigdvtfvmIZsiROQpcYorXX1ru4PsqFnZILgFaN7M0gLfy")
                .role("ADMIN")
                .build();
        this.clientRepository.save(client);
    }
}
}
