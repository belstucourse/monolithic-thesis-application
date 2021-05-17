package com.belstu.thesisproject.generator;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.dto.workday.PsychoAvailableTimeslotDto;
import javax.validation.constraints.NotNull;

public interface PsychoWorkdayGenerator {
  PsychoAvailableTimeslotDto generateWorkday(@NotNull final PsychoWorkday psychoWorkday);
}
