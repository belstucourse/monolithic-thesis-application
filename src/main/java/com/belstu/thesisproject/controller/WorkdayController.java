package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.dto.workday.PsychoAvailableTimeslotDto;
import com.belstu.thesisproject.dto.workday.PsychoWorkdayDto;
import com.belstu.thesisproject.generator.PsychoWorkdayGenerator;
import com.belstu.thesisproject.mapper.WorkdayMapper;
import com.belstu.thesisproject.service.WorkdayService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/timeslot")
@AllArgsConstructor
public class WorkdayController {
    private final WorkdayService workdayService;
    private final PsychoWorkdayGenerator workdayGenerator;
    private final WorkdayMapper workdayMapper;
    private final Integer ONE_WEEK = 1;

    @GetMapping("/{psychoId}")
    public PsychoAvailableTimeslotDto getTimeSlotsOfPsycho(
            @PathVariable String psychoId, @RequestParam LocalDate workingDate) {
        final PsychoWorkday psychoWorkday =
                workdayService.getWorkdayByPsychoIdAndWorkingDate(psychoId, workingDate);
        return workdayGenerator.generateWorkday(psychoWorkday);
    }

    @GetMapping("/{psychoId}/workday")
    public List<PsychoWorkdayDto> getWorkdayOfPsychoOnWeek(
            @PathVariable String psychoId) {
        final List<PsychoWorkday> workdayOfPsychoOnWeek = workdayService.getWorkdayOfPsychoOnWeek(psychoId);
        return workdayMapper.mapToDtoList(workdayOfPsychoOnWeek);
    }

    @GetMapping("/{psychoId}/week")
    public List<PsychoAvailableTimeslotDto> getTimeSlotsOfPsychoOnWeek(
            @PathVariable String psychoId) {
        final LocalDate endDate = now().plusWeeks(ONE_WEEK);
        return now().datesUntil(endDate, Period.ofDays(1))
                .map(date -> workdayService.getWorkdayByPsychoIdAndWorkingDate(psychoId, date))
                .map(workdayGenerator::generateWorkday).collect(Collectors.toList());
    }

    @PostMapping
    public PsychoWorkdayDto saveWorkdayOfPsychologist(
            @Valid @RequestBody PsychoWorkdayDto psychoWorkdayDto) {
        final PsychoWorkday workday = workdayMapper.map(psychoWorkdayDto);
        return workdayMapper.map(workdayService.save(workday));
    }

    @PutMapping
    public PsychoWorkdayDto updateWorkday(
            @Valid @RequestBody PsychoWorkdayDto psychoWorkdayDto) {
        final PsychoWorkday workday = workdayMapper.map(psychoWorkdayDto);
        return workdayMapper.map(workdayService.save(workday));
    }

    @DeleteMapping("/{psychoId}")
    @ResponseStatus(OK)
    public void deleteWorkdayOfPsychologist(
            @PathVariable String psychoId, @RequestParam LocalDate date) {
        workdayService.delete(psychoId, date);
    }
}
