package com.harry.indentity_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.harry.indentity_service.entity.Product;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemResponse {
    Long id;
    Product product;
    int quantity;

    double amountCartItem;
}
