package com.shoppingcart.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppingcart.app.model.Order;
import com.shoppingcart.app.model.PaymentOrder;

@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    List<PaymentOrder> findByOrder(Order order);
}
