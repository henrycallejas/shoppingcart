package com.shoppingcart.app.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.shoppingcart.app.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentOrderDto {
    private Long paymentOrderId;
    private Long orderId;
    private Integer amount;
    private BigDecimal totalAmount;
    private BigDecimal unitPrice;
    private PaymentStatus status;
    private Date paymentDate;
    private String cardNumber;
    private String cvv;
    private Integer expiringMonth;
    private Integer expiringYear;
    private List<OrderDetailDto> details;
}
