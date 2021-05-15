package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.repository.PsychoWorkdayRepository;
import com.belstu.thesisproject.service.WorkdayService;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class WorkdayServiceImpl implements WorkdayService {
    private final PsychoWorkdayRepository workdayRepository;

    @SneakyThrows
    public PsychoWorkday getWorkdayByPsychoIdAndWorkingDate(@NotNull final String id, @NotNull final LocalDate date) {
        return workdayRepository.findByDateAndPsychologistId(date, id).orElseThrow(() -> new NotFoundException("Workday not found"));
    }
}
