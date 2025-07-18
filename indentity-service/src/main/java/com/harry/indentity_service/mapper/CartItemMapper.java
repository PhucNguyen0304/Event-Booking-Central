package com.harry.indentity_service.mapper;

import org.mapstruct.Mapper;

import com.harry.indentity_service.dto.request.CartItemRequest;
import com.harry.indentity_service.dto.response.CartItemResponse;
import com.harry.indentity_service.entity.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem toEntity(CartItemRequest req);

    CartItemResponse toCartItemResponse(CartItem entity);
}
