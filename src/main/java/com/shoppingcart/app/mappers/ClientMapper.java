package com.shoppingcart.app.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.shoppingcart.app.dto.ClientDto;
import com.shoppingcart.app.entities.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "clientId", source = "clientId")
    ClientDto toDto(Client client);

    List<ClientDto> toDtoList(List<Client> clients);
}
