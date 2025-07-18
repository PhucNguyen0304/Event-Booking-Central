package com.harry.identity.repository.httpClient;

import com.harry.identity.config.AuthenticationRequestInterceptor;
import com.harry.identity.config.FeignMultipartSupportConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    List<String> uploadAvatar(
            @RequestPart("file")   List<MultipartFile> files,
            @RequestPart("folder") String folder
    );
}