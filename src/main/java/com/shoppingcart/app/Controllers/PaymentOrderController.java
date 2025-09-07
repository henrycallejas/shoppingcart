package com.shoppingcart.app.Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/payment-orders")
public class PaymentOrderController {
    @Autowired
    PaymentOrderService paymentOrderService;

    @Operation(summary = "Get all payment orders", description = "Retrieve a list of all payment orders")
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

    @Operation(summary = "Get payment order by ID", description = "Retrieve a payment order by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@Parameter(description = "ID of the payment order to retrieve") @PathVariable Long id) {
        try {
            PaymentOrder order = this.paymentOrderService.getPaymentOrderById(id);
            if (order != null) {
                return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), order);
            } else {
                return ApiResponse.jsonResponse(HttpStatus.NOT_FOUND, ResponseMessage.NO_CONTENT.getMessage(), null);
            }
        } catch (Exception e) {
            return ApiResponse.jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.ERROR.getMessage(), e.getMessage());
        }
    }

    @Operation(summary = "Process a payment order", description = "Process a payment order for a specific client")
    @PostMapping("/{clientId}")
    public ResponseEntity<Map<String, Object>> processPayment(
    @Parameter(description = "ID of the client making the payment")
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

    @Operation(summary = "Update an existing payment order", description = "Update the details of an existing payment order")
    @PutMapping
    public ResponseEntity<Map<String, Object>> updatePaymentOrder(@RequestBody PaymentOrderDto order) {
        PaymentOrder updatedOrder = this.paymentOrderService.updatePaymentOrder(order);
        if (updatedOrder != null) {
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.UPDATED.getMessage(), updatedOrder);
        } else {
            return ApiResponse.jsonResponse(HttpStatus.NOT_FOUND, ResponseMessage.NO_CONTENT.getMessage(), null);
        }
    }

    @Operation(summary = "Delete a payment order", description = "Delete a payment order by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePaymentOrder(@Parameter(description = "ID of the payment order to delete") @PathVariable Long id) {
        boolean isDeleted = this.paymentOrderService.deletePaymentOrder(id);
        if (isDeleted) {
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.DELETED.getMessage(), id);
        } else {
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.NOT_FOUND.getMessage(), null);
        }
    }
}
