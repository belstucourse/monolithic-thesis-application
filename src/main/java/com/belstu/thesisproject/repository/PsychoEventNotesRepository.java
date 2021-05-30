package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.workday.PsychoEventNotes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PsychoEventNotesRepository extends JpaRepository<PsychoEventNotes, String> {
    Optional<PsychoEventNotes> findByEventId(String eventId);
}
