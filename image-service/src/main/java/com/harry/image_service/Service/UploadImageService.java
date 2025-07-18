package com.harry.image_service.Service;

import com.cloudinary.Cloudinary;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadImageService {
    CloudinaryService cloudinaryService;

    public List<String> uploadImages(List<MultipartFile> files, String folder) {
        List<String> listImgUrl = files.stream()
                .map(file -> cloudinaryService.uploadImage(file, folder))
                .collect(Collectors.toList());

        log.info("List image URL: " + listImgUrl);
        return listImgUrl;
    }
}
