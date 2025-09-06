package com.shoppingcart.app.dto;

import java.util.Date;
import java.util.List;

import com.shoppingcart.app.model.Client;
import com.shoppingcart.app.model.OrderDetail;
import com.shoppingcart.app.model.PaymentOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long orderId;
    private Client client;
    private Date orderDate;
    private List<PaymentOrder> paymentOrders;
    private List<OrderDetail> orderDetails;
}
