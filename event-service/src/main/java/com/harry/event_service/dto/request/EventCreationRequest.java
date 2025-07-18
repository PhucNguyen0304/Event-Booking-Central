package com.harry.event_service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.harry.event_service.dto.EventType;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventCreationRequest {
    @NotBlank
    String nameEvent;

    // Date of the event: yyyy-MM-dd
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    // Time of the event: HH:mm
    @NotNull
    @JsonFormat(pattern = "HH:mm")
    LocalTime time;
    // e.g. “Conference Hall A”

    @NotBlank
    String city;

    @NotBlank
    String detailedAddress;  // e.g. “123 Nguyễn Huệ, Q.1, TP.HCM”

    @NotNull
    @Positive
    Integer totalTickets;

    @NotNull
    @PositiveOrZero
    Integer remainingTickets;

    @NotNull
    @DecimalMin("0.0")
    BigDecimal price;

    @NotNull
    String eventType;

}
