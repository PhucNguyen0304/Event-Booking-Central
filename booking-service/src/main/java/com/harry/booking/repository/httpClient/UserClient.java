package com.harry.booking.repository.httpClient;

import com.harry.booking.config.AuthenticationRequestInterceptor;
import com.harry.booking.dto.ApiResponse;
import com.harry.booking.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service",
            url = "${app.services.user}",
            configuration = {
        AuthenticationRequestInterceptor.class
            })
public interface UserClient {
    @GetMapping("/my-info/{userId}")
    ApiResponse<UserResponse> getMyInfo(@PathVariable("userId") String userId);
}
