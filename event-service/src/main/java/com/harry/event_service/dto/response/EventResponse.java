package com.harry.event_service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.harry.event_service.dto.EventType;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
