package com.belstu.thesisproject.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.belstu.thesisproject.domain.file.FileStorageAttribute;
import com.belstu.thesisproject.dto.FileType;
import com.belstu.thesisproject.repository.FileStorageRepository;
import com.belstu.thesisproject.service.AmazonService;
import com.belstu.thesisproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class AmazonServiceImpl implements AmazonService {
    private final AmazonS3 s3client;
    private final FileStorageRepository fileStorageRepository;
    private final UserService userService;
    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Override
    @Transactional
    public String uploadFile(
            @NotNull MultipartFile multipartFile, @NotNull String objectId, @NotNull FileType type) {

        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        final FileStorageAttribute storageAttribute;
        if (type == FileType.AVATAR || type == FileType.POST) {
            storageAttribute = fileStorageRepository.findByObjectId(objectId)
                    .orElseGet(() -> FileStorageAttribute.builder().objectId(objectId).fileType(type).build());
            storageAttribute.setUrl(fileUrl);
        } else {
            storageAttribute =
                    FileStorageAttribute.builder().objectId(objectId).url(fileUrl).fileType(type).build();
        }
        fileStorageRepository.save(storageAttribute);
        return fileUrl;
    }

    @Override
    public void deleteFileFromS3Bucket(@NotBlank String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
    }

    public String getSertificateUrl(String objectId, FileType fileType) {
        return fileStorageRepository.findByObjectIdAndFileType(objectId, fileType).get().getUrl();
    }

    private String generateFileName(MultipartFile multiPart) {
        String uuidFile = UUID.randomUUID().toString();
        return uuidFile + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(
                new PutObjectRequest(bucketName, fileName, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
