package com.harry.image_service.controller;

import com.harry.image_service.Service.UploadImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UploadImageController {
    UploadImageService uploadImageService;

    @GetMapping
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/upload")
    public List<String> uploadImage(@RequestParam("file") List<MultipartFile> files,
                                    @RequestParam("folder") String folder) {
        if(files.isEmpty() || files.size() > 4 ) {
            throw new RuntimeException("Size list file least is 1 and maximun is 4");
        }
        log.info("Upload image contreoller");
        return uploadImageService.uploadImages(files, folder);

    }
}
