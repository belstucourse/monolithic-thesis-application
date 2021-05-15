package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public interface WorkdayService {
    PsychoWorkday getWorkdayByPsychoIdAndWorkingDate(@NotNull final String id, @NotNull final LocalDate date);
}
