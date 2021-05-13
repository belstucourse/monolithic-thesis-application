package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.service.AmazonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/storage")
@AllArgsConstructor
public class S3BucketController {
    private final AmazonService amazonService;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonService.uploadFile(file);
    }

    @ResponseStatus(OK)
    @DeleteMapping("/deleteFile")
    public void deleteFile(@RequestPart(value = "url") String fileUrl) {
        this.amazonService.deleteFileFromS3Bucket(fileUrl);
    }

}
