package com.harry.indentity_service.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Builder
@NoArgsConstructor()
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /** Nhiều CartItem → Một Cart (cart.username lẫy từ khóa chính của Cart) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_username")
    @JsonIgnore
    Cart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    Product product;

    int quantity;
    double amountCartItem;
}
