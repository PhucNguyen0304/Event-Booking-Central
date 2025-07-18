package com.harry.indentity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harry.indentity_service.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, String> {
    boolean existsByUsername(String username);
}
