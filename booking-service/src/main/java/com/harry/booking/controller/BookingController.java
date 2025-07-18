package com.harry.booking.controller;

import com.harry.booking.dto.ApiResponse;
import com.harry.booking.dto.request.BookingRequest;
import com.harry.booking.dto.response.BookingResponse;
import com.harry.booking.service.BookingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class BookingController {
    BookingService bookingService;

    @GetMapping
    String getBooking() {
        return "This is Get Method";
    }

    @PostMapping
    ApiResponse<BookingResponse> create(@RequestBody BookingRequest request) {
        log.info("Booking controller " + request);
        return ApiResponse.<BookingResponse>builder()
                .result(bookingService.create(request))
                .build();
    }
}
