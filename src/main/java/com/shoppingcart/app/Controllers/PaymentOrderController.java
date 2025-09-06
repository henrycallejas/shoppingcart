package com.shoppingcart.app.Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.app.dto.PaymentOrderDto;
import com.shoppingcart.app.enums.ResponseMessage;
import com.shoppingcart.app.model.Order;
import com.shoppingcart.app.model.PaymentOrder;
import com.shoppingcart.app.services.PaymentOrderService;
import com.shoppingcart.app.utils.ApiResponse;

@RestController
@RequestMapping("/payment-orders")
public class PaymentOrderController {
    @Autowired
    PaymentOrderService paymentOrderService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        try {
            List<PaymentOrder> orders = this.paymentOrderService.getAllPaymentOrders();
            if (!orders.isEmpty()) {
                return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), orders);
            }
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.EMPTY.getMessage(), orders);
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(),
                    e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable Long id) {
        try {
            PaymentOrder order = this.paymentOrderService.getPaymentOrderById(id);
            if (order != null) {
                return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), order);
            } else {
                return ApiResponse.jsonResponse(HttpStatus.NOT_FOUND, ResponseMessage.NOT_FOUND.getMessage(), null);
            }
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), e.getMessage());
        }
    }

    @PostMapping("/{clientId}")
    public ResponseEntity<Map<String, Object>> processPayment(
    @PathVariable Long clientId, @RequestBody PaymentOrderDto order) {
        Map<String, Object> response = this.paymentOrderService.processPayment(clientId, order);
        Order createdOrder = new Order();
        Map<String, Object> errorResponse = new HashMap<>();

        try {
            createdOrder = (Order) response.get("response");
            errorResponse.put("response", createdOrder);
            return ApiResponse.jsonResponse(HttpStatus.CREATED, ResponseMessage.CREATED.getMessage(), errorResponse);
        } catch (Exception e) {
            String resp = (String) response.get("response");
            return ApiResponse.jsonResponse(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR.getMessage(), resp);
        }
    }

    @PutMapping
    public ResponseEntity<PaymentOrder> updatePaymentOrder(@RequestBody PaymentOrderDto order) {
        PaymentOrder updatedOrder = this.paymentOrderService.updatePaymentOrder(order);
        if (updatedOrder != null) {
            return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
