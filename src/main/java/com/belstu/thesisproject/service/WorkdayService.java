package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface WorkdayService {
  PsychoWorkday getWorkdayByPsychoIdAndWorkingDate(
      @NotNull final String id, @NotNull final LocalDate date);

  PsychoWorkday save(@NotNull PsychoWorkday workday);

  PsychoWorkday update(@NotNull PsychoWorkday workday);

  void delete(@NotNull String psychoId, @NotNull LocalDate date);

  List<PsychoWorkday> getWorkdayOfPsychoOnWeek(String psychoId);

  List<PsychoWorkday> saveAll(@NotNull Collection<PsychoWorkday> workdays);
}
