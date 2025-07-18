package com.harry.indentity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harry.indentity_service.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsByname(String name);

    boolean existsById(String id);
}
