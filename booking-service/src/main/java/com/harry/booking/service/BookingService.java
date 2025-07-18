package com.harry.booking.service;

import com.harry.booking.dto.request.BookingRequest;
import com.harry.booking.dto.response.BookingResponse;
import com.harry.booking.dto.response.UserResponse;
import com.harry.booking.exception.AppException;
import com.harry.booking.exception.ErrorCode;
import com.harry.booking.repository.httpClient.EventClient;
import com.harry.booking.repository.httpClient.UserClient;
import com.harry.event.dto.Booking;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class BookingService {
    KafkaTemplate<String, Object> kafkaTemplate;
    EventClient eventClient;
    UserClient userClient;
    HttpServletRequest httpServletRequest;

    public BookingResponse create(BookingRequest request) {
        log.info("Create Booking Service {}", request);

        String currentJwtToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Token {}", currentJwtToken);

        String userId = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        var user  = userClient.getMyInfo(userId);
        var event = eventClient.getEvent(request.getEventId());

        if (event.getResult().getRemainingTickets() < request.getTicketCount()) {
            throw new AppException(ErrorCode.NOT_ENOUGHT_TICKETS);
        }

        Booking booking = Booking.builder()
                .userId(userId)
                .userName(user.getResult().getUsername())
                .eventId(event.getResult().getId())
                .eventName(event.getResult().getNameEvent())
                .ticketCount(request.getTicketCount())
                .totalPrice(
                        BigDecimal.valueOf(request.getTicketCount())
                                .multiply(event.getResult().getPrice())
                )
                .build();

        log.info("Booking before send to kafka {}", booking);

        Message<Booking> msg = MessageBuilder
                .withPayload(booking)
                .setHeader(KafkaHeaders.TOPIC, "booking")
                .setHeader(HttpHeaders.AUTHORIZATION, currentJwtToken)
                .build();

        log.info("MSG " + msg);
        kafkaTemplate.send(msg);

        BookingResponse bookingResponse = BookingResponse.builder()
                .userId(userId)
                .userName(user.getResult().getUsername())
                .eventId(event.getResult().getId())
                .eventName(event.getResult().getNameEvent())
                .ticketCount(request.getTicketCount())
                .totalPrice(
                        BigDecimal.valueOf(request.getTicketCount())
                                .multiply(event.getResult().getPrice())
                )
                .build();

        log.info("Booking response {}", bookingResponse);
        return bookingResponse;
    }
}
