package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.repository.PsychoWorkdayRepository;
import com.belstu.thesisproject.service.WorkdayService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkdayServiceImpl implements WorkdayService {
    private final PsychoWorkdayRepository workdayRepository;

    @SneakyThrows
    @Override
    public PsychoWorkday getWorkdayByPsychoIdAndWorkingDate(
            @NotNull final String id, @NotNull final LocalDate date) {
        Psychologist psychologist = new Psychologist();
        psychologist.setId(id);
        final PsychoWorkday psychoWorkday = PsychoWorkday.builder()
                .date(date)
                .psychologist(psychologist)
                .build();
        return workdayRepository
                .findByDateAndPsychologistId(date, id)
                .orElse(psychoWorkday);
    }

    @Override
    public PsychoWorkday save(PsychoWorkday workday) {
        return workdayRepository.save(workday);
    }

    @Override
    public PsychoWorkday update(@NotNull PsychoWorkday workday) {
        return workdayRepository.save(workday);
    }

    @Override
    public void delete(@NotNull String psychoId, @NotNull LocalDate date) {
        workdayRepository.deleteByPsychologistIdAndDate(psychoId, date);
    }

    @Override
    public List<PsychoWorkday> getWorkdayOfPsychoOnWeek(String psychoId) {
        final LocalDate endDate = LocalDate.now();
        final LocalDate startDate = endDate.minusWeeks(1);
        return workdayRepository.findByDateGreaterThanEqualAndDateLessThanEqual(startDate, endDate);
    }
}
