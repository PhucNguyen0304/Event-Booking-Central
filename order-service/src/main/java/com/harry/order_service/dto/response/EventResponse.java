package com.harry.order_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventResponse {
    String id;

    String nameEvent;

    LocalDate date;

    LocalTime time;

    String city;

    String detailedAddress;

    Integer totalTickets;

    Integer remainingTickets;

    BigDecimal price;

    String imgEvent;

    String eventType;
}
