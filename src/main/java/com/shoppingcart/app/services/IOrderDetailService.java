package com.shoppingcart.app.services;

import java.util.List;

import com.shoppingcart.app.model.OrderDetail;

public interface IOrderDetailService {
    List<OrderDetail> getAllOrderDetails();
    OrderDetail getOrderDetailById(Long id);
    OrderDetail createOrderDetail(OrderDetail orderDetail);
    OrderDetail updateOrderDetail(OrderDetail orderDetail);
    void deleteOrderDetail(Long id);
}
