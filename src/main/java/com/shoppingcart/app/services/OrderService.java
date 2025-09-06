package com.shoppingcart.app.services;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcart.app.dto.OrderDetailDto;
import com.shoppingcart.app.model.Client;
import com.shoppingcart.app.model.Order;
import com.shoppingcart.app.model.OrderDetail;
import com.shoppingcart.app.model.Product;
import com.shoppingcart.app.repositories.ClientRepository;
import com.shoppingcart.app.repositories.OrderDetailRepository;
import com.shoppingcart.app.repositories.OrderRepository;
import com.shoppingcart.app.repositories.ProductRepository;
import com.shoppingcart.app.exception.ClientNotFoundException;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Order> getAllOrders() {
       return this.orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return this.orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public OrderDetail createOrder(OrderDetailDto orderDto) {
        Client client = clientRepository.findById(orderDto.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(orderDto.getClientId()));

        Order newOrder = Order.builder()
                .client(client)
                .orderDate(new Date())
                .build();

        orderRepository.save(newOrder);

        Product product = productRepository.findById(orderDto.getProductId())
                .orElseGet(() -> {
                    Product newProduct = Product.builder()
                            .name(orderDto.getOrderDetail().getProduct().getName())
                            .description(orderDto.getOrderDetail().getProduct().getDescription())
                            .price(orderDto.getOrderDetail().getProduct().getPrice())
                            .image(orderDto.getOrderDetail().getProduct().getImage())
                            .build();
                    return productRepository.save(newProduct);
                });

        OrderDetail orderDetail = OrderDetail.builder()
                .order(newOrder)
                .product(product)
                .amount(orderDto.getAmount())
                .unitPrice(orderDto.getUnitPrice())
                .build();

        return orderDetailRepository.save(orderDetail);
    }
    
}
