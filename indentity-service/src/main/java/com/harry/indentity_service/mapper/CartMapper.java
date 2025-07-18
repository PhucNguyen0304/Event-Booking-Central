package com.harry.indentity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.harry.indentity_service.dto.request.CartRequest;
import com.harry.indentity_service.dto.response.CartResponse;
import com.harry.indentity_service.entity.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {
    Cart toCart(CartRequest request);

    @Mapping(target = "msg", ignore = true)
    CartResponse toCartResponse(Cart cart);
}
