package com.shoppingcart.app.repositories;

import org.springframework.stereotype.Repository;

import com.shoppingcart.app.entities.Order;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
