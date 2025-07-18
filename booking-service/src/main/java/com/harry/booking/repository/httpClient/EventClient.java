package com.harry.booking.repository.httpClient;


import com.harry.booking.config.AuthenticationRequestInterceptor;
import com.harry.booking.dto.ApiResponse;
import com.harry.booking.dto.response.EventResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(
        name = "event-service",
        url = "${app.services.event}",
        configuration = {
                AuthenticationRequestInterceptor.class
        }
)
public interface EventClient {
    @GetMapping("/{eventId}")
    ApiResponse<EventResponse> getEvent(@PathVariable("eventId") String eventId);

}
