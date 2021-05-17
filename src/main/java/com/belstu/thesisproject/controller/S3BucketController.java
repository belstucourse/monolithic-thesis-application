package com.belstu.thesisproject.controller;

import static org.springframework.http.HttpStatus.OK;

import com.belstu.thesisproject.dto.FileType;
import com.belstu.thesisproject.service.AmazonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/storage")
@AllArgsConstructor
public class S3BucketController {
  private final AmazonService amazonService;

  @PostMapping
  public String uploadFile(@RequestParam MultipartFile file,
                           @RequestParam String objectId,
                           @RequestParam FileType type) {
    return this.amazonService.uploadFile(file, objectId, type);
  }

  @ResponseStatus(OK)
  @DeleteMapping
  public void deleteFile(@RequestParam(value = "url") String fileUrl) {
    this.amazonService.deleteFileFromS3Bucket(fileUrl);
  }
}
