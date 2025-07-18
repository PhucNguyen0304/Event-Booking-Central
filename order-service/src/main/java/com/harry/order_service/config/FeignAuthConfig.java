package com.harry.order_service.config;

import com.harry.order_service.dto.TokenContext;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

@Configuration
public class FeignAuthConfig {
    @Bean
    public RequestInterceptor authenticationRequestInterceptor() {
        return template -> {
            String jwt = TokenContext.getToken();
            if (StringUtils.hasText(jwt)) {
                template.header(HttpHeaders.AUTHORIZATION, jwt);
            }
        };
    }
}
