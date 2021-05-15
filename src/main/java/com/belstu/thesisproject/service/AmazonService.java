package com.belstu.thesisproject.service;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface AmazonService {
    String uploadFile(@NotNull MultipartFile multipartFile);

    void deleteFileFromS3Bucket(@NotBlank String fileUrl);
}
