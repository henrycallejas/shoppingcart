package com.shoppingcart.app.services;

import java.util.List;
import java.util.Map;

import com.shoppingcart.app.dto.PaymentOrderDto;
import com.shoppingcart.app.model.PaymentOrder;

public interface IPaymentOrderService {
    List<PaymentOrder> getAllPaymentOrders();
    PaymentOrder getPaymentOrderById(Long id);
    Map<String, Object> processPayment(Long clientId, PaymentOrderDto order);
    PaymentOrder updatePaymentOrder(PaymentOrderDto order);
}
