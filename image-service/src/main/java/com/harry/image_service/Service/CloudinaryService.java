package com.harry.image_service.Service;

import com.cloudinary.Cloudinary;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CloudinaryService {
    Cloudinary cloudinary;
    public String uploadImage(MultipartFile file, String folder){
        try {
            Map<?, ?> result = cloudinary.uploader()
                    .upload(file.getBytes(), Map.of("folder", folder));
            return result.get("secure_url").toString();
        } catch (IOException ex) {
            log.info("Error when upload file to cloudinary");
            throw new RuntimeException("Upload ảnh thất bại", ex);
        }
    }
}
