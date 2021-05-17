package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.repository.PsychoWorkdayRepository;
import com.belstu.thesisproject.service.WorkdayService;
import java.time.LocalDate;
import javassist.NotFoundException;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkdayServiceImpl implements WorkdayService {
  private final PsychoWorkdayRepository workdayRepository;

  @SneakyThrows
  @Override
  public PsychoWorkday getWorkdayByPsychoIdAndWorkingDate(
      @NotNull final String id, @NotNull final LocalDate date) {
    return workdayRepository
        .findByDateAndPsychologistId(date, id)
        .orElseThrow(() -> new NotFoundException("Workday not found"));
  }

  @Override
  public PsychoWorkday save(PsychoWorkday workday) {
    return workdayRepository.save(workday);
  }

  @Override
  public void delete(@NotNull String psychoId, @NotNull LocalDate date) {
    workdayRepository.deleteByPsychologistIdAndDate(psychoId, date);
  }
}
