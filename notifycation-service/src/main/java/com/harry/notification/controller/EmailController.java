package com.harry.notification.controller;

import com.harry.notification.dto.request.EmailRequest;
import com.harry.notification.dto.response.ApiResponse;
import com.harry.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    private final EmailService mailService;

    @PostMapping("/simple")
    public ApiResponse<Void> sendSimple(@RequestBody EmailRequest request) {
        log.info("Simple controller");
        String message = mailService.sendEmail(request);
        return ApiResponse.<Void>builder()
                .message(message)
                .build();
    }

    @PostMapping("/html")
    public String sendHtml(@RequestParam String to,
                           @RequestParam String subject,
                           @RequestBody String htmlContent) throws Exception {
        mailService.sendHtmlEmail(to, subject, htmlContent);
        return "HTML email sent";
    }
}
