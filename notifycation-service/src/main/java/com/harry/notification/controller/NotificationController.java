package com.harry.notification.controller;
import com.harry.event.dto.NotificationEmail;
import com.harry.notification.dto.request.EmailRequest;
import com.harry.notification.dto.response.ApiResponse;
import com.harry.notification.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    EmailService emailService;

    @KafkaListener(topics = "notification-email-v3")
    public ApiResponse<Void> listenNotificationDelivery(NotificationEmail notificationEmail){
        log.info("Simple controller");

        String message = emailService.sendEmail(EmailRequest.builder()
                .to(notificationEmail.getTo())
                .subject(notificationEmail.getSubject())
                .text(notificationEmail.getText())
                .build());
        return ApiResponse.<Void>builder()
                .message(message)
                .build();
    }
}

