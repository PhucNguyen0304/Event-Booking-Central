package com.harry.order_service.mapper;

import com.harry.event.dto.Booking;
import com.harry.order_service.dto.response.OrderResponse;
import com.harry.order_service.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(Booking booking);
    OrderResponse toOrderResponse(Order order);

}
