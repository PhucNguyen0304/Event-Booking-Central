package com.harry.indentity_service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String id;
    String name;
    String author;
    int remain;
    double price;
    int discount;

    String imageUrl;
    @JsonIgnore
    String msg;
}
