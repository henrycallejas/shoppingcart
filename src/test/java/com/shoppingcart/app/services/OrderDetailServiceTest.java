package com.shoppingcart.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shoppingcart.app.model.OrderDetail;
import com.shoppingcart.app.repositories.OrderDetailRepository;

@ExtendWith(MockitoExtension.class)
class OrderDetailServiceTest {

    @Mock
    private OrderDetailRepository orderDetailRepository;

    @InjectMocks
    private OrderDetailService orderDetailService;

    @Test
    void testGetAllOrderDetails() {
        // Arrange: Create an OrderDetails for testing
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setAmount(2);
        orderDetail1.setUnitPrice(new BigDecimal("100.0"));
        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setAmount(3);
        orderDetail2.setUnitPrice(new BigDecimal("150.99"));

        when(orderDetailRepository.findAll()).thenReturn(java.util.Arrays.asList(orderDetail1, orderDetail2));

        // Act: call the service method
        java.util.List<OrderDetail> result = orderDetailService.getAllOrderDetails();

        // Assert: verify the results are as expected
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getAmount());
        assertEquals(new BigDecimal("100.0"), result.get(0).getUnitPrice());
        assertEquals(3, result.get(1).getAmount());
        assertEquals(new BigDecimal("150.99"), result.get(1).getUnitPrice());

        // verify that the repository method was called
        verify(orderDetailRepository).findAll();
    }
    @Test
    void testGetOrderDetailById() {
        OrderDetail mockDetail = new OrderDetail();
        mockDetail.setAmount(2);
        mockDetail.setUnitPrice(new BigDecimal("50.0"));
        mockDetail.setOrderDetailId(1L);
        when(orderDetailRepository.findById(1L)).thenReturn(Optional.of(mockDetail));

        OrderDetail result = orderDetailService.getOrderDetailById(1L);

        assertNotNull(result);
        assertEquals(2, result.getAmount());
        assertEquals(new BigDecimal("50.0"), result.getUnitPrice());

        // Simulate that the repository does not find the OrderDetail with ID 99
        when(orderDetailRepository.findById(99L)).thenReturn(Optional.empty());
        OrderDetail result2 = orderDetailService.getOrderDetailById(99L);

        assertNull(result2); // The method returns null when the object is not found
    }

    @Test
    void testCreateOrderDetail() {
        // Arrange: Create an OrderDetail for testing
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setUnitPrice(new BigDecimal("50.0"));
        orderDetail.setAmount(5);

        // Simulate the repository behavior
        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);

        // Act: call the service method
        OrderDetail result = orderDetailService.createOrderDetail(orderDetail);

        // Assert: verify the results are as expected
        assertNotNull(result);
        assertEquals(5, result.getAmount());
        assertEquals(new BigDecimal("50.0"), result.getUnitPrice());

        // verify that the repository method was called
        verify(orderDetailRepository).save(orderDetail);
    }

    @Test
    void testUpdateOrderDetail() {
        // Arrange
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setAmount(10);
        orderDetail.setUnitPrice(new BigDecimal("20.0"));
        orderDetail.setOrderDetailId(1L);

        when(orderDetailRepository.existsById(orderDetail.getOrderDetailId())).thenReturn(true);
        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(orderDetail);
        // Act
        OrderDetail result = orderDetailService.updateOrderDetail(orderDetail);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.getAmount());
        assertEquals(new BigDecimal("20.0"), result.getUnitPrice());
        verify(orderDetailRepository).save(orderDetail);
    }

    @Test
    void testDeleteOrderDetail() {
        Long id = 1L;

        // Act
        orderDetailService.deleteOrderDetail(id);

        // Assert: verify that the repository method was called
        verify(orderDetailRepository).deleteById(id);
    }
}
