package com.harry.order_service.controller;

import com.harry.event.dto.Booking;
import com.harry.order_service.dto.ApiResponse;
import com.harry.order_service.dto.TokenContext;
import com.harry.order_service.dto.response.OrderResponse;
import com.harry.order_service.entity.Order;
import com.harry.order_service.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {
    OrderService orderService;

    @KafkaListener(topics = "booking")
    public ApiResponse<OrderResponse> listenBooking(Message<Booking> msg) {
        String jwtToken = msg.getHeaders()
                .get(HttpHeaders.AUTHORIZATION, String.class);
        log.info("Listen Booking Controller");
        TokenContext.setToken(jwtToken);
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.create(msg.getPayload()))
                .build();
    }

    @GetMapping("{orderId}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable("orderId") String orderId) {
        log.info("Get Order By Id Controller");
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrder(orderId))
                .build();
    }

    @GetMapping("/event/{eventId}")
    public ApiResponse<List<OrderResponse>> getOrderByEventId(@PathVariable("eventId") String eventId) {
        log.info("Get Orders By Event Id");

        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByEventId(eventId))
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrderResponse>> getOrderByUserId(@PathVariable("userId") String userId) {
        log.info("Get Orders By User Id");

        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrdersByUser(userId))
                .build();
    }
}