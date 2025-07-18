package com.harry.event_service.controller;

import com.fasterxml.jackson.annotation.JsonValue;
import com.harry.event_service.dto.ApiResponse;
import com.harry.event_service.dto.EventType;
import com.harry.event_service.dto.request.EventCreationRequest;
import com.harry.event_service.dto.response.EventResponse;
import com.harry.event_service.service.EventService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class EventController {
    EventService eventService;

    @PostMapping(value = "/create",   consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<EventResponse> create(@RequestParam("nameEvent") String nameEvent,
                                      @RequestParam("city") String city,
                                      @RequestParam("detailedAddress") String detailedAddress,
                                      @RequestParam("totalTickets") Integer totalTickets,
                                      @RequestParam("remainingTickets") Integer remainingTickets,
                                      @RequestParam("price") BigDecimal price,
                                      @RequestParam("eventType") String eventType,
                                      @RequestParam("file") MultipartFile file) {
        log.info("Create event controller ");
        EventCreationRequest request = EventCreationRequest.builder()
                .nameEvent(nameEvent)
                .city(city)
                .detailedAddress(detailedAddress)
                .totalTickets(totalTickets)
                .remainingTickets(remainingTickets)
                .price(price)
                .eventType(eventType)
                .build();
        log.info("Request " + request);

        return ApiResponse.<EventResponse>builder()
                .result(eventService.create(request, file))
                .build();
    }

    @GetMapping
    ApiResponse<List<EventResponse>> getEvents() {
        return ApiResponse.<List<EventResponse>>builder()
                .result(eventService.getEvents())
                .build();
    }

    @GetMapping("/{eventId}")
    ApiResponse<EventResponse> getEvent(@PathVariable String eventId) {
        return ApiResponse.<EventResponse>builder()
                .result(eventService.getEvent(eventId))
                .build();
    }

    @PatchMapping("/{eventId}")
    ApiResponse<Boolean> updateEvent(@PathVariable("eventId") String eventId, @RequestParam("ticketCount") int ticketCount) {
        eventService.updateTicketCount(eventId, ticketCount);
        return ApiResponse.<Boolean>builder()
                .result(true)
                .build();
    }
}
//ApiResponse<Boolean> updateTicketCount(@PathVariable("eventId") String eventId,@RequestParam("ticketCount") int ticketCount);

