package com.harry.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {
    String userId;
    String userName;
    String eventId;
    String eventName;
    Integer ticketCount;
    BigDecimal totalPrice;
}
