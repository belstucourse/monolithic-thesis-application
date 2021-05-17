package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.file.FileStorageAttribute;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStorageRepository extends JpaRepository<FileStorageAttribute, String> {
  Optional<FileStorageAttribute> findByObjectId(final String objectId);
}
