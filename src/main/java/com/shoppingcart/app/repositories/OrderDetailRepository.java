package com.shoppingcart.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.app.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
