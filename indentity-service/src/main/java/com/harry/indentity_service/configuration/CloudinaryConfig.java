package com.harry.indentity_service.configuration;

import java.util.Map;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        try {
            return new Cloudinary(Map.of(
                    "cloud_name", cloudName,
                    "api_key",     apiKey,
                    "api_secret",  apiSecret,
                    "secure",      "true"
            ));
        } catch (Exception ex) {
            log.error("Khởi tạo Cloudinary thất bại", ex);
            throw new BeanCreationException("cloudinary", ex);
        }
    }
}
