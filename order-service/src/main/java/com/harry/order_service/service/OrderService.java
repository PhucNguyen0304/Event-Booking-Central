package com.harry.order_service.service;

import com.harry.event.dto.Booking;
import com.harry.order_service.dto.response.OrderResponse;
import com.harry.order_service.entity.Order;
import com.harry.order_service.exception.AppException;
import com.harry.order_service.exception.ErrorCode;
import com.harry.order_service.mapper.OrderMapper;
import com.harry.order_service.repository.HttpClient.EventClient;
import com.harry.order_service.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    EventClient eventClient;
    OrderMapper orderMapper;
    OrderRepository orderRepository;

    public OrderResponse create(Booking booking) {
        log.info("Create Order Service" + booking);
        var eventUpdate = eventClient.updateTicketCount(booking.getEventId(),booking.getTicketCount());
        if(!(eventUpdate.getResult() == true)) {
            throw new AppException(ErrorCode.NOT_UPDATE_TICKETCOUNT);
        }
        Order order = orderMapper.toOrder(booking);

        order.setTime(LocalTime.now());
        order.setDate(LocalDate.now());


        log.info("Order befor save " + order);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @PostAuthorize("returnObject.userId == authentication.name")
    public OrderResponse getOrder(String orderId) {
        log.info("Get Order By Order Id");
        Order order = orderRepository.findById(orderId)
                        .orElseThrow(() ->  new AppException(ErrorCode.ORDER_NOT_FOUND));

        log.info("Order " + order);
        return orderMapper.toOrderResponse(order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getOrdersByEventId(String eventId) {
        log.info("Get List Order By EventID" + eventId);
        List<OrderResponse> orders = orderRepository.findAllByEventId(eventId).stream()
                .map(order -> orderMapper.toOrderResponse(order))
                .toList();
        log.info("List order response find by eventid " + orders);
        return  orders;
    }

    @PreAuthorize("#userId == authentication.name")
    public List<OrderResponse> getOrdersByUser(String userId) {
        log.info("Get list Order By User Id");
        List<OrderResponse> orders = orderRepository.findAllByUserId(userId).stream()
                .map(order -> orderMapper.toOrderResponse(order))
                .toList();

        log.info("List order resonse find by user Id" + orders);
        return orders;
    }
}
