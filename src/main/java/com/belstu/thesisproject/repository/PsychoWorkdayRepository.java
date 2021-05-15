package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PsychoWorkdayRepository extends JpaRepository<PsychoWorkday, String> {
    Optional<PsychoWorkday> findByDateAndPsychologistId(LocalDate date, String psychoId);
}
