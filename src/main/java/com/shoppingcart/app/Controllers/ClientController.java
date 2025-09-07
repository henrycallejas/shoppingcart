package com.shoppingcart.app.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.app.dto.ClientDto;
import com.shoppingcart.app.entities.Client;
import com.shoppingcart.app.enums.ResponseMessage;
import com.shoppingcart.app.mappers.ClientMapper;
import com.shoppingcart.app.services.ClientService;
import com.shoppingcart.app.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    @Operation(summary = "Get all clients", description = "Retrieve a list of all clients")
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllClients() {
        try {
            List<Client> clients = this.clientService.getAllClients();
            List<ClientDto> clientDTOs = clientMapper.toDtoList(clients);
            if (!clientDTOs.isEmpty()) {
                return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), clientDTOs);
            }
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.EMPTY.getMessage(), null);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), null);
        }
    }

    @Operation(summary = "Get client by ID", description = "Retrieve a client by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getClientById(@Parameter(description = "ID of the client to retrieve") @PathVariable Long id) {
        try {
            Client client = this.clientService.getClientById(id);
            ClientDto clientDto = this.clientMapper.toDto(client);
            if (clientDto != null) {
                return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), clientDto);
            } else {
                return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.EMPTY.getMessage(), null);
            }
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), null);
        }
    }

    @Operation(summary = "Create a new client", description = "Create a new client with the provided details")
    @PostMapping
    public ResponseEntity<Map<String, Object>> createClient(@RequestBody Client client) {
        try {
            Client createdClient = this.clientService.createClient(client);
            ClientDto createdClientDto = this.clientMapper.toDto(createdClient);
            return ApiResponse.jsonResponse(HttpStatus.CREATED, ResponseMessage.CREATED.getMessage(), createdClientDto);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), null);
        }
    }

    @Operation(summary = "Update an existing client", description = "Update the details of an existing client")
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateClient(@RequestBody Client client) {
        try {
            Client existingClient = this.clientService.getClientById(client.getClientId());
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
                    .orders(existingClient.getOrders())
                    .build();
            this.clientService.updateClient(updatedClient);
            ClientDto clientDto = this.clientMapper.toDto(updatedClient);
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), clientDto);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), null);
        }
    }

    @Operation(summary = "Delete a client", description = "Delete a client by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteClient(@Parameter(description = "ID of the client to delete") @PathVariable Long id) {
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
