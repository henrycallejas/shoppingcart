package com.shoppingcart.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppingcart.app.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
