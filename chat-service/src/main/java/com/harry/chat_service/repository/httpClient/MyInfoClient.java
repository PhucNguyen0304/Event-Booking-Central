package com.harry.chat_service.repository.httpClient;

import com.harry.chat_service.configuration.AuthenticationRequestInterceptor;
import com.harry.chat_service.dto.ApiResponse;
import com.harry.chat_service.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "identity-service", url = "${app.services.identity.url}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface MyInfoClient {
    @GetMapping("/users/my-info/{userId}")
    ApiResponse<UserResponse> getMyInfor(@PathVariable("userId") String userId);
}

