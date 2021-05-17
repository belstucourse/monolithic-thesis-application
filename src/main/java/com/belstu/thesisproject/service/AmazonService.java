package com.belstu.thesisproject.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.belstu.thesisproject.dto.FileType;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonService {
  String uploadFile(@NotNull MultipartFile multipartFile, String objectId, FileType type);

  void deleteFileFromS3Bucket(@NotBlank String fileUrl);
}
