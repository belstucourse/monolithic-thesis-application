package com.belstu.thesisproject.domain.file;

import com.belstu.thesisproject.dto.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

@Entity(name = "files_attribute")
@Table(name = "files_attribute")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FileStorageAttribute {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    private String url;

    private String objectId;

    @Column(name = "type", nullable = false, length = 50, updatable = false)
    @Enumerated(STRING)
    private FileType fileType;
}
