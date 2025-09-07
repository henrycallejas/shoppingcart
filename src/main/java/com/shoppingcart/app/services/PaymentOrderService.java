package com.shoppingcart.app.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcart.app.dto.OrderDetailDto;
import com.shoppingcart.app.dto.PaymentOrderDto;
import com.shoppingcart.app.entities.Client;
import com.shoppingcart.app.entities.Order;
import com.shoppingcart.app.entities.OrderDetail;
import com.shoppingcart.app.entities.PaymentOrder;
import com.shoppingcart.app.entities.Product;
import com.shoppingcart.app.enums.PaymentStatus;
import com.shoppingcart.app.repositories.ClientRepository;
import com.shoppingcart.app.repositories.OrderRepository;
import com.shoppingcart.app.repositories.PaymentOrderRepository;
import com.shoppingcart.app.repositories.ProductRepository;
import com.shoppingcart.app.utils.CardValidator;

@Service
public class PaymentOrderService implements IPaymentOrderService{

    @Autowired
    PaymentOrderRepository paymentOrderRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<PaymentOrder> getAllPaymentOrders() {
        return this.paymentOrderRepository.findAll();
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) {
        return this.paymentOrderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Map<String, Object> processPayment(Long clientId, PaymentOrderDto detailsDto) {
        Map<String, Object> response = new HashMap<>();
        String expiringDate = CardValidator.getExpiringDate(detailsDto);

        if (CardValidator.isCardNumberValid(detailsDto.getCardNumber())
                && CardValidator.isExpiringDateValid(expiringDate)) {
            Client client = this.clientRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client not found"));
            Order newOrder = Order.builder()
                    .client(client)
                    .orderDate(new Date())
                    .build();
            List<OrderDetail> details = new ArrayList<>();
            List<OrderDetailDto> orderDetailsDto = detailsDto.getDetails();
            if (orderDetailsDto == null || orderDetailsDto.isEmpty()) {
                response.put("response", "There is no order detail to process.");
                return response;
            }
            details = orderDetailsDto.stream().map(dto -> {
                OrderDetail detail = OrderDetail.builder()
                        .amount(dto.getAmount())
                        .unitPrice(dto.getUnitPrice())
                        .order(newOrder)
                        .build();

            Product product = this.productRepository.findById(dto.getProductId())
            .orElseThrow(() -> new RuntimeException("Product not found: " + dto.getProductId()));
                detail.setProduct(product);
                return detail;
            }).collect(Collectors.toList());
            newOrder.setOrderDetails(details);
            Order orderCreated = this.orderRepository.save(newOrder);

            PaymentOrder paymentOrder = PaymentOrder.builder()
                    .paymentDate(new Date())
                    .status(PaymentStatus.APROBADO)
                    .build();
            BigDecimal totalAmount = details.stream()
                    .map(d -> d.getUnitPrice().multiply(BigDecimal.valueOf(d.getAmount())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            paymentOrder.setTotalAmount(totalAmount);
            paymentOrder.setOrder(orderCreated);
            this.paymentOrderRepository.save(paymentOrder);

            List<PaymentOrder> paymentOrders = this.paymentOrderRepository.findByOrder(orderCreated);
            orderCreated.setPaymentOrders(paymentOrders);
            response.put("response", orderCreated);
            return response;
        }
        if (!CardValidator.isCardNumberValid(detailsDto.getCardNumber())) {
            response.put("response", "Número de tarjeta no válido.");
            return response;
        } else if (!CardValidator.isExpiringDateValid(expiringDate)) {
            response.put("response", "Fecha de expiración no válida.");
            return response;
        }
        return response;
    }

    @Override
    public PaymentOrder updatePaymentOrder(PaymentOrderDto order) {
       Optional<PaymentOrder> actualPaymentOrder = this.paymentOrderRepository.findById(order.getPaymentOrderId());
        Optional<Order> actualOrder = this.orderRepository.findById(order.getOrderId());
        if (actualPaymentOrder.isPresent() && actualOrder.isPresent()) {
            PaymentOrder item = actualPaymentOrder.get();
            item.setStatus(order.getStatus());
            Order newOrder = actualOrder.get();
            item.setOrder(newOrder);
            return this.paymentOrderRepository.save(item);
        }
        return null;
    }
    
    public boolean deletePaymentOrder(Long id) {
        Optional<PaymentOrder> paymentOrder = this.paymentOrderRepository.findById(id);
        if (paymentOrder.isPresent()) {
            this.paymentOrderRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
