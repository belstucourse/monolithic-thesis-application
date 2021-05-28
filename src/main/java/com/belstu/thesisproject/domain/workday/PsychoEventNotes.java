package com.belstu.thesisproject.domain.workday;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "notes")
@NoArgsConstructor
@Data
public class PsychoEventNotes {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    private String symptoms;

    private String specialNotes;

    @OneToOne(mappedBy = "psychoEventNotes")
    private Event event;
}
