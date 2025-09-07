package com.shoppingcart.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shoppingcart.app.dto.OrderDetailDto;
import com.shoppingcart.app.entities.Client;
import com.shoppingcart.app.entities.Order;
import com.shoppingcart.app.entities.OrderDetail;
import com.shoppingcart.app.entities.Product;
import com.shoppingcart.app.repositories.ClientRepository;
import com.shoppingcart.app.repositories.OrderDetailRepository;
import com.shoppingcart.app.repositories.OrderRepository;
import com.shoppingcart.app.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(new Order(), new Order()));

        List<Order> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(2, orders.size());
    }

    @Test
    void testGetOrderById() {
        Long orderId = 1L;
        Order mockOrder = new Order();
        mockOrder.setOrderId(orderId);
        mockOrder.setClient(null); 

        when(orderRepository.findById(orderId)).thenReturn(java.util.Optional.of(mockOrder));

        Order order = orderService.getOrderById(orderId);

        assertNotNull(order);
        assertEquals(orderId, order.getOrderId());
    }

    @Test
    void testCreateOrder() {
        // Arrange
        Long clientId = 1L;
        Long productId = 2L;

        // Mock Client
        Client mockClient = new Client();
        mockClient.setClientId(clientId);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(mockClient));

        // Mock Product (does not exists, so it will be created)
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Mock Product to be created
        Product newProduct = new Product();
        newProduct.setProductId(productId);
        newProduct.setName("Test Product");
        newProduct.setDescription("Test Desc");
        newProduct.setPrice(BigDecimal.valueOf(100));
        newProduct.setImage("img.jpg");
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        // Mock Order
        Order newOrder = new Order();
        newOrder.setOrderId(10L);
        newOrder.setClient(mockClient);
        newOrder.setOrderDate(new Date());
        when(orderRepository.save(any(Order.class))).thenReturn(newOrder);

        // Mock OrderDetail
        OrderDetail savedOrderDetail = new OrderDetail();
        savedOrderDetail.setOrder(newOrder);
        savedOrderDetail.setProduct(newProduct);
        savedOrderDetail.setAmount(5);
        savedOrderDetail.setUnitPrice(BigDecimal.valueOf(100));
        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(savedOrderDetail);

        // Build OrderDetailDto
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setClientId(clientId);
        orderDetailDto.setProductId(productId);
        orderDetailDto.setAmount(5);
        orderDetailDto.setUnitPrice(BigDecimal.valueOf(100));

        // If the product does not exist, you must set the OrderDetail and Product in the DTO
        OrderDetail orderDetail = new OrderDetail();
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Desc");
        product.setPrice(BigDecimal.valueOf(100));
        product.setImage("img.jpg");
        orderDetail.setProduct(product);
        orderDetailDto.setOrderDetail(orderDetail);

        // Act
        OrderDetail result = orderService.createOrder(orderDetailDto);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.getAmount());
        assertEquals("Test Product", result.getProduct().getName());
        assertEquals(clientId, result.getOrder().getClient().getClientId());
    }
}