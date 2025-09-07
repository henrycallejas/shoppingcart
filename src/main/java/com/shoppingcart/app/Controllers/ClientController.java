package com.shoppingcart.app.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.app.enums.ResponseMessage;
import com.shoppingcart.app.model.Client;
import com.shoppingcart.app.services.ClientService;
import com.shoppingcart.app.utils.ApiResponse;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllClients() {
        try {
            List<Client> clients = this.clientService.getAllClients();
            if (!clients.isEmpty()) {
                return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), clients);
            }
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.EMPTY.getMessage(), null);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getClientById(@PathVariable Long id) {
        try {
            Client client = this.clientService.getClientById(id);
            if (client != null) {
                return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), client);
            } else {
                return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.EMPTY.getMessage(), null);
            }
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateClient(@PathVariable Long id, @RequestBody Client client) {
        try {
            Client existingClient = this.clientService.getClientById(id);
            if (existingClient == null) {
                return ApiResponse.jsonResponse(HttpStatus.NO_CONTENT, ResponseMessage.NOT_FOUND.getMessage(), null);
            }
            Client updatedClient = Client.builder()
                    .clientId(existingClient.getClientId())
                    .name(client.getName())
                    .lastName(client.getLastName())
                    .email(client.getEmail())
                    .address(client.getAddress())
                    .username(client.getUsername())
                    .password(client.getPassword())
                    .role(client.getRole())
                    .build();
            this.clientService.updateClient(updatedClient);
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), updatedClient);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteClient(@PathVariable Long id) {
        try {
            Client existingClient = this.clientService.getClientById(id);
            if (existingClient == null) {
                return ApiResponse.jsonResponse(HttpStatus.NO_CONTENT, ResponseMessage.NOT_FOUND.getMessage(), null);
            }
            this.clientService.deleteClient(id);
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.DELETED.getMessage(), null);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), null);
        }
    }
}
