package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsychoWorkdayRepository extends JpaRepository<PsychoWorkday, String> {
  Optional<PsychoWorkday> findByDateAndPsychologistId(LocalDate date, String psychoId);
  List<PsychoWorkday> findByDateGreaterThanEqualAndDateLessThanEqualAndPsychologistId(LocalDate startDate, LocalDate endDate, String psychoId);
  void deleteByPsychologistIdAndDate(String psychoId, LocalDate date);
}
