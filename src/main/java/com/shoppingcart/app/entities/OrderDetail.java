package com.shoppingcart.app.entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ORDER_DETAIL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_DETAIL_ID")
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "AMOUNT")
    private Integer amount;

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;
}
