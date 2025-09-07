package com.shoppingcart.app.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shoppingcart.app.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PAYMENT_ORDER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAYMENT_ORDER_ID")
    private Long paymentOrderId;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    @JsonBackReference
    private Order order;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "PAYMENT_DATE")
    private Date paymentDate;

    @Enumerated(EnumType.STRING) // Guarda como texto (VARCHAR)
    @Column(nullable = false)
    private PaymentStatus status;

}
