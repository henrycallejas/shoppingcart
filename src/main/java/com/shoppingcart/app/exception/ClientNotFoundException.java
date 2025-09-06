package com.shoppingcart.app.exception;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(Long clientId) {
        super("Client with ID " + clientId + " not found.");
    }
    
}
