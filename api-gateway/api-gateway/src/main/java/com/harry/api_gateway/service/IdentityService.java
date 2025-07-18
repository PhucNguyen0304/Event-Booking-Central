package com.harry.api_gateway.service;


import com.harry.api_gateway.dto.ApiResponse;
import com.harry.api_gateway.dto.request.IntrospectRequest;
import com.harry.api_gateway.dto.response.IntrospectResponse;
import com.harry.api_gateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class IdentityService {
    IdentityClient identityClient;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token){
        String url = "/identity/auth/introspect";
        log.info(">> Calling IdentityService.introspect at {}", url);
        return identityClient.introspect(IntrospectRequest.builder()
                        .token(token)
                .build());
    }
}
