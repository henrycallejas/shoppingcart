package com.shoppingcart.app.Controllers;

import java.util.List;
import java.util.Map;

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

import com.shoppingcart.app.enums.ResponseMessage;
import com.shoppingcart.app.model.OrderDetail;
import com.shoppingcart.app.services.OrderDetailService;
import com.shoppingcart.app.utils.ApiResponse;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    // Define your endpoints here
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        if (orderDetails.isEmpty()) {
            return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.EMPTY.getMessage(), null);
        }
        return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), orderDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrderDetailById(@PathVariable Long id) {
        OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
        if (orderDetail == null) {
            return ApiResponse.jsonResponse(HttpStatus.NOT_FOUND, ResponseMessage.NOT_FOUND.getMessage(), null);
        }
        return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), orderDetail);
    }
    
    @PutMapping
    public ResponseEntity<Map<String, Object>> updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        OrderDetail updatedOrderDetail = orderDetailService.updateOrderDetail(orderDetail);
        if (updatedOrderDetail == null) {
            return ApiResponse.jsonResponse(HttpStatus.NOT_FOUND, ResponseMessage.NOT_FOUND.getMessage(), null);
        }
        return ApiResponse.jsonResponse(HttpStatus.OK, ResponseMessage.OK.getMessage(), updatedOrderDetail);
    }
}
