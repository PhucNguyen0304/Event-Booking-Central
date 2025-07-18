package com.harry.order_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String userId;
    String userName;
    String eventId;
    String eventName;
    Integer ticketCount;
    BigDecimal totalPrice;

    @Column(nullable = false)
    LocalTime time;

    @Column(nullable = false)
    LocalDate date;
}
