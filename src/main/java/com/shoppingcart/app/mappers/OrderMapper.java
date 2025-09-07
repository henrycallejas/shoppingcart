package com.shoppingcart.app.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.shoppingcart.app.dto.OrderDto;
import com.shoppingcart.app.entities.Order;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface OrderMapper {

    OrderDto toDto(Order order);
    List<OrderDto> toDtoList(List<Order> orders);
}
