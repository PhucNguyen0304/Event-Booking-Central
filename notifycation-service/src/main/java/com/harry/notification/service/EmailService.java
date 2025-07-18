package com.harry.notification.service;

import com.harry.notification.dto.request.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    // Gửi email plain text
    public String sendEmail(EmailRequest request) {
        log.info("Send email simple service");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("minqtv24@gmail.com");
        message.setTo(request.getTo());
        message.setSubject(request.getSubject());
        message.setText(request.getText());
        log.info("Message: {}" , message);
        mailSender.send(message);
        String msg = String.format("Please check your %s email account", message.getFrom());
        return msg;
    }

    // Gửi email HTML hoặc có attachment
    public void sendHtmlEmail(String to, String subject, String htmlContent) throws Exception {
        MimeMessage mimeMsg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);  // true = HTML
        mailSender.send(mimeMsg);
    }
}
