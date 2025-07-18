package com.harry.event_service.repository.httpClient;

import com.harry.event_service.config.AuthenticationRequestInterceptor;
import com.harry.event_service.config.FeignMultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(
        name = "image-service",
        url = "${app.services.image}",
        configuration = {
                FeignMultipartSupportConfig.class,
                AuthenticationRequestInterceptor.class
        }
)
public interface ImageClient {

    @PostMapping(
            value    = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<String> uploadImage(
            @RequestPart("file")   List<MultipartFile> files,
            @RequestPart("folder") String folder
    );
}