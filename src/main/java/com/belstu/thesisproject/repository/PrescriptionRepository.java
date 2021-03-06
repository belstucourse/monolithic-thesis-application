package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.workday.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    Optional<Prescription> findByEventId(String eventId);
}
