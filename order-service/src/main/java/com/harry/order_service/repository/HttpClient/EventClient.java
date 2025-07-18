package com.harry.order_service.repository.HttpClient;

import com.harry.order_service.config.AuthenticationRequestInterceptor;
import com.harry.order_service.config.FeignAuthConfig;
import com.harry.order_service.config.FeignClientConfig;
import com.harry.order_service.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "event-service",
                url = "${app.services.event}",
                configuration = {
                FeignAuthConfig.class,
                        FeignClientConfig.class
                })
public interface EventClient {
    @PatchMapping("/{eventId}")
    ApiResponse<Boolean> updateTicketCount(@PathVariable("eventId") String eventId,@RequestParam("ticketCount") int ticketCount);
}

