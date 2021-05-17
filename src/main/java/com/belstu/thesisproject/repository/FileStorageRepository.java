package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.file.FileStorageAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileStorageRepository extends JpaRepository<FileStorageAttribute, String> {
    Optional<FileStorageAttribute> findByObjectId(final String objectId);
}
