package com.harry.indentity_service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartTotalResponse {
    int totalQuantity;
    double totalAmount;

    @JsonIgnore
    String msg;
}
