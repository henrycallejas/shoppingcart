package com.shoppingcart.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shoppingcart.app.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUsername(String username);

    @Query("select c from Client c where c.username = ?1 and c.password = ?2")
    Optional<Client> findByUsernameAndPassword(String username, String password);
}
