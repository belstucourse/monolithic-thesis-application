package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.dto.workday.PsychoAvailableTimeslotDto;
import com.belstu.thesisproject.dto.workday.PsychoWorkdayDto;
import com.belstu.thesisproject.generator.PsychoWorkdayGenerator;
import com.belstu.thesisproject.mapper.WorkdayMapper;
import com.belstu.thesisproject.repository.PsychoWorkdayRepository;
import com.belstu.thesisproject.service.EventService;
import com.belstu.thesisproject.service.WorkdayService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/timeslot")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class WorkdayController {
    private final WorkdayService workdayService;
    private final EventService eventService;
    private final PsychoWorkdayGenerator workdayGenerator;
    private final WorkdayMapper workdayMapper;
    private final Integer ONE_WEEK = 1;
    private final PsychoWorkdayRepository psychoWorkdayRepository;


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

        var res = now().datesUntil(endDate, Period.ofDays(1))
                .map(date -> workdayService.getWorkdayByPsychoIdAndWorkingDate(psychoId, date))
                .map(workdayGenerator::generateWorkday).collect(Collectors.toList());

        var psychoEvent = eventService.getByPsychoId(psychoId);

        for (var workday : res) {
            for (var busySlot : psychoEvent) {
                workday.getSlots().removeIf(x -> x.isEqual(busySlot.getDate()));
            }
        }

        return res;
    }

    @PostMapping
    @Transactional
    public PsychoWorkdayDto saveWorkdayOfPsychologist(
            @Valid @RequestBody PsychoWorkdayDto psychoWorkdayDto) {
        final PsychoWorkday workday = workdayMapper.map(psychoWorkdayDto);
        final PsychoWorkday persistedWorkday = psychoWorkdayRepository.findByDateAndPsychologistId(workday.getDate(), workday.getPsychologist().getId()).orElse(workday);
        persistedWorkday.setStartDateTime(workday.getStartDateTime());
        persistedWorkday.setEndDateTime(workday.getEndDateTime());
        persistedWorkday.setDate(workday.getDate());
        return workdayMapper.map(workdayService.save(persistedWorkday));
    }


    //Не смотреть, полная хуйня
    @PostMapping("/all")
    @Transactional
    public List<PsychoWorkdayDto> saveWorkdayOfPsychologistOnWeek(
            @Valid @RequestBody PsychoWorkdayDto psychoWorkdayDto) {
        PsychoWorkday workday = workdayMapper.map(psychoWorkdayDto);
        final LocalDate startDate = psychoWorkdayDto.getDate();
        final List<LocalDate> dates = startDate.datesUntil(startDate.plusWeeks(1)).collect(Collectors.toList());
        final Psychologist psychologist = workday.getPsychologist();
        final List<PsychoWorkday> resultWorkdays = new ArrayList<>();
        LocalDateTime startDateTime = workday.getStartDateTime();
        LocalDateTime endDateTime = workday.getEndDateTime();
        for (LocalDate date : dates) {
            workday.setDate(date);
            if (psychoWorkdayRepository.findByDateAndPsychologistId(date, psychologist.getId()).isEmpty()) {
                resultWorkdays.add(workday);
            }
            workday = new PsychoWorkday();
            workday.setPsychologist(psychologist);
            startDateTime = startDateTime.plusDays(1);
            endDateTime = endDateTime.plusDays(1);
            workday.setStartDateTime(startDateTime);
            workday.setEndDateTime(endDateTime);
        }

        final List<PsychoWorkday> persistedWorkdays = workdayService.saveAll(resultWorkdays);
        return workdayMapper.mapToDtoList(persistedWorkdays);
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
