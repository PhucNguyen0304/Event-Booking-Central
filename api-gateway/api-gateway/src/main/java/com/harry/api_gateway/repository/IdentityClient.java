package com.harry.api_gateway.repository;

import com.harry.api_gateway.dto.ApiResponse;
import com.harry.api_gateway.dto.request.IntrospectRequest;
import com.harry.api_gateway.dto.response.IntrospectResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;


public interface IdentityClient {

    @PostExchange(url = "/identity/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
