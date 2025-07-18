package com.harry.indentity_service.dto.response;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.harry.indentity_service.entity.CartItem;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    @Id
    String username;

    List<CartItem> items = new ArrayList<>();

    @JsonIgnore
    String msg;
}
