package com.harry.indentity_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harry.indentity_service.entity.Cart;
import com.harry.indentity_service.entity.CartItem;
import com.harry.indentity_service.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    void deleteAllByIdIn(List<Long> cartItemIds);
}
