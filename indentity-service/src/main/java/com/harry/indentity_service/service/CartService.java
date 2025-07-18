package com.harry.indentity_service.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.harry.indentity_service.dto.request.CartDeleteItemsRequest;
import com.harry.indentity_service.dto.request.CartRequest;
import com.harry.indentity_service.dto.response.CartItemResponse;
import com.harry.indentity_service.dto.response.CartOnlyItemResponse;
import com.harry.indentity_service.dto.response.CartResponse;
import com.harry.indentity_service.dto.response.CartTotalResponse;
import com.harry.indentity_service.entity.Cart;
import com.harry.indentity_service.entity.CartItem;
import com.harry.indentity_service.entity.Product;
import com.harry.indentity_service.exception.AppException;
import com.harry.indentity_service.exception.ErrorCode;
import com.harry.indentity_service.mapper.CartItemMapper;
import com.harry.indentity_service.mapper.CartMapper;
import com.harry.indentity_service.repository.CartItemRepository;
import com.harry.indentity_service.repository.CartRepository;
import com.harry.indentity_service.repository.ProductRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepo;
    ProductRepository productRepo;
    CartItemRepository itemRepo;
    CartMapper cartMapper;
    CartItemMapper cartItemMapper;

    @Transactional
    public CartOnlyItemResponse addToCart(CartRequest request) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        String productId = request.getProductId();
        int qty = request.getQuantity();

        Cart cart = cartRepo.findById(username).orElseGet(() -> {
            Cart c = new Cart();
            c.setUsername(username);
            return c;
        });

        Product product =
                productRepo.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        CartItem item = itemRepo.findByCartAndProduct(cart, product).orElseGet(() -> {
            CartItem ci = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(0)
                    .amountCartItem(0.0)
                    .build();
            cart.getItems().add(ci);
            return ci;
        });

        item.setQuantity(item.getQuantity() + qty);
        item.setAmountCartItem(item.getProduct().getPrice() * item.getQuantity());
        log.info("item amount " + item.getAmountCartItem());
        CartItemResponse cartItemResponse = cartItemMapper.toCartItemResponse(itemRepo.save(item));
        CartResponse cartResponse = cartMapper.toCartResponse(cartRepo.save(cart));

        String msg = String.format(
                "Added %s product into %s's shopping cart",
                cartItemResponse.getProduct().getName(), username);
        log.info("Amount cart item" + cartItemResponse.getAmountCartItem());
        return CartOnlyItemResponse.builder()
                .username(username)
                .product(cartItemResponse)
                .msg(msg)
                .build();
    }

    @Transactional
    public CartOnlyItemResponse addOneToItemQuantity(String productId) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        Cart cart = cartRepo.findById(username).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        Product product =
                productRepo.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = itemRepo.findByCartAndProduct(cart, product).orElseThrow(() -> new RuntimeException());

        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItem.setAmountCartItem(cartItem.getProduct().getPrice() * cartItem.getQuantity());

        CartItemResponse cartItemResponse = cartItemMapper.toCartItemResponse(itemRepo.save(cartItem));
        cartRepo.save(cart);

        String msg = String.format(
                "In %s’s shopping cart, the quantity of the %s product has been increased by one.",
                username, cartItemResponse.getProduct().getName());

        return CartOnlyItemResponse.builder()
                .username(username)
                .product(cartItemResponse)
                .msg(msg)
                .build();
    }

    @Transactional
    public CartOnlyItemResponse reduceOneToItemQuantity(String productId) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        Cart cart = cartRepo.findById(username).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        Product product =
                productRepo.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = itemRepo.findByCartAndProduct(cart, product).orElseThrow(() -> new RuntimeException());

        cartItem.setQuantity(cartItem.getQuantity() - 1);
        cartItem.setAmountCartItem(cartItem.getProduct().getPrice() * cartItem.getQuantity());

        if (cartItem.getQuantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found");
        }
        CartItemResponse cartItemResponse = cartItemMapper.toCartItemResponse(itemRepo.save(cartItem));
        cartRepo.save(cart);

        String msg = String.format(
                "In %s’s shopping cart, the quantity of the %s product has been decrease  by one.",
                username, cartItemResponse.getProduct().getName());

        // 5. Lưu cart nếu mới và trả về
        return CartOnlyItemResponse.builder()
                .username(username)
                .product(cartItemResponse)
                .msg(msg)
                .build();
    }

    public CartResponse getCart() {
        log.info("Get cart service");
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        Cart cart = cartRepo.findById(username).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
        CartResponse cartResponse = cartMapper.toCartResponse(cart);

        String msg = String.format("All products in %s’s shopping cart have been retrieved.", username);

        return CartResponse.builder()
                .username(cartResponse.getUsername())
                .items(cartResponse.getItems())
                .msg(msg)
                .build();
    }

    public String deleteCartItem(String productId) {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        Cart cart = cartRepo.findById(username).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        Product product =
                productRepo.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = itemRepo.findByCartAndProduct(cart, product).orElseThrow(() -> new RuntimeException());

        String msg = String.format(
                "%s products have been deleted from %s's shopping cart",
                cartItem.getProduct().getName(), username);

        cart.getItems().remove(cartItem);
        itemRepo.delete(cartItem);

        return msg;
    }

    public String deleteCartItems(CartDeleteItemsRequest request) {

        if (request.getCartItemIds() == null || request.getCartItemIds().isEmpty()) {
            return "The number of products to be deleted must be greater than 1";
        }

        List<Long> cartItemIds = request.getCartItemIds();

        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        Cart cart = cartRepo.findById(username).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        var cartItems = itemRepo.findAllById(cartItemIds);

        List<String> listProductNameDeleted = cartItems.stream()
                .map(cartItem -> cartItem.getProduct().getName())
                .collect(Collectors.toList());

        String joinedProductName = String.join(",", listProductNameDeleted);

        String msg = String.format("Delete all %s products from %s's cart ", joinedProductName, username);

        cart.getItems().removeAll(cartItems);
        itemRepo.deleteAll(cartItems);

        return msg;
    }

    public CartTotalResponse cartTotal() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        Cart cart = cartRepo.findById(username).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        if (cart.getItems().size() < 1) {
            return CartTotalResponse.builder()
                    .msg(String.format("%s's shopping cart has no product", username))
                    .build();
        }

        int totalQuantity = 0;
        double totalAmount = 0;

        for (int i = 0; i < cart.getItems().size(); i++) {
            totalQuantity = totalQuantity + cart.getItems().get(i).getQuantity();
            totalAmount = totalAmount
                    + (cart.getItems().get(i).getProduct().getPrice()
                            * cart.getItems().get(i).getQuantity());
        }

        return CartTotalResponse.builder()
                .totalQuantity(totalQuantity)
                .totalAmount(totalAmount)
                .msg(String.format(
                        "%s's cart has a total of %d products with a total amount of %f",
                        username, totalQuantity, totalAmount))
                .build();
    }
}
