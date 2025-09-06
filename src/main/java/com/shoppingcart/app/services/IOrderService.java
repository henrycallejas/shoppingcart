package com.shoppingcart.app.services;

import java.util.List;

import com.shoppingcart.app.dto.OrderDetailDto;
import com.shoppingcart.app.model.Order;
import com.shoppingcart.app.model.OrderDetail;

public interface IOrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    OrderDetail createOrder(OrderDetailDto order);
    
}
