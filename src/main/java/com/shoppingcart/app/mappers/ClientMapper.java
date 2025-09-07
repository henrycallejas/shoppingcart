package com.shoppingcart.app.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.shoppingcart.app.dto.ClientDto;
import com.shoppingcart.app.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "clientId", source = "clientId")
    ClientDto toDto(Client client);

}
