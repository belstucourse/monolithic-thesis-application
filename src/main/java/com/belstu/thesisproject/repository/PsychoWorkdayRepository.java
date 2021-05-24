package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsychoWorkdayRepository extends JpaRepository<PsychoWorkday, String> {
  Optional<PsychoWorkday> findByDateAndPsychologistId(LocalDate date, String psychoId);
  List<PsychoWorkday> findByDateGreaterThanEqualAndDateLessThanEqual(LocalDate startDate, LocalDate endDate);
  void deleteByPsychologistIdAndDate(String psychoId, LocalDate date);
}
