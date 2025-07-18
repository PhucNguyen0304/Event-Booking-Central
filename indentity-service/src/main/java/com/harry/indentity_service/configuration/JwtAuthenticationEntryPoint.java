package com.harry.indentity_service.configuration;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harry.indentity_service.dto.response.ApiResponse;
import com.harry.indentity_service.exception.ErrorCode;

/**
 * public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
 * @Override
 * public void commence(
 * HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
 * throws IOException, ServletException {
 * ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
 *
 * response.setStatus(errorCode.getStatusCode().value());
 * response.setContentType(MediaType.APPLICATION_JSON_VALUE);
 *
 * ApiResponse<?> apiResponse = ApiResponse.builder()
 * .code(errorCode.getCode())
 * .message(errorCode.getMessage())
 * .build();
 *
 * ObjectMapper objectMapper = new ObjectMapper();
 *
 * response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
 * response.flushBuffer();
 * }
 * }*/
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
            throws IOException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

        response.setStatus(errorCode.getStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
