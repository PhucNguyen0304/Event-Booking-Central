package com.harry.indentity_service.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.harry.indentity_service.dto.request.CartDeleteItemsRequest;
import com.harry.indentity_service.dto.request.CartRequest;
import com.harry.indentity_service.dto.response.ApiResponse;
import com.harry.indentity_service.dto.response.CartOnlyItemResponse;
import com.harry.indentity_service.dto.response.CartResponse;
import com.harry.indentity_service.dto.response.CartTotalResponse;
import com.harry.indentity_service.service.CartService;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cart")
@Validated
@Builder
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CartController {
    CartService cartService;

    @PostMapping("/items")
    public ApiResponse<CartOnlyItemResponse> addItem(@RequestBody CartRequest req) {
        CartOnlyItemResponse cartOnlyItemResponse = cartService.addToCart(req);
        return ApiResponse.<CartOnlyItemResponse>builder()
                .message(cartOnlyItemResponse.getMsg())
                .result(cartOnlyItemResponse)
                .build();
    }
    /** Increment 1 quantity product*/
    @PostMapping("/items/{productId}/increment")
    public ApiResponse<CartOnlyItemResponse> increamentItem(@PathVariable("productId") String productId) {
        CartOnlyItemResponse cartOnlyItemResponse = cartService.addOneToItemQuantity(productId);
        return ApiResponse.<CartOnlyItemResponse>builder()
                .message(cartOnlyItemResponse.getMsg())
                .result(cartOnlyItemResponse)
                .build();
    }

    /** Decrease 1 quantity product */
    @PostMapping("/items/{productId}/decrease")
    public ApiResponse<CartOnlyItemResponse> decreaseItem(@PathVariable("productId") String productId) {
        CartOnlyItemResponse cartOnlyItemResponse = cartService.reduceOneToItemQuantity(productId);
        return ApiResponse.<CartOnlyItemResponse>builder()
                .message(cartOnlyItemResponse.getMsg())
                .result(cartOnlyItemResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<CartResponse> getCart() {
        CartResponse cartResponse = cartService.getCart();
        return ApiResponse.<CartResponse>builder()
                .message(cartResponse.getMsg())
                .result(cartResponse)
                .build();
    }

    @DeleteMapping("/items")
    public ApiResponse<Void> deleteCartItems(@RequestBody CartDeleteItemsRequest request) {
        String msg = cartService.deleteCartItems(request);
        return ApiResponse.<Void>builder().message(msg).build();
    }

    @DeleteMapping("/items/{productId}")
    public ApiResponse<Void> deleteCartItem(@PathVariable("productId") String productId) {
        String msg = cartService.deleteCartItem(productId);
        return ApiResponse.<Void>builder().message(msg).build();
    }

    // Get Total quantity product and total amount have in cart
    @PostMapping("/items/totals")
    public ApiResponse<CartTotalResponse> deleteCartItem() {
        CartTotalResponse cartTotalResponse = cartService.cartTotal();
        return ApiResponse.<CartTotalResponse>builder()
                .message(cartTotalResponse.getMsg())
                .result(cartTotalResponse)
                .build();
    }
}
