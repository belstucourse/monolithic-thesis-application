package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.dto.workday.PsychoAvailableTimeslotDto;
import com.belstu.thesisproject.generator.PsychoWorkdayGenerator;
import com.belstu.thesisproject.service.WorkdayService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WorkdayController {
    private final WorkdayService workdayService;
    private final PsychoWorkdayGenerator workdayGenerator;

    @GetMapping("timeslot/{psychoId}")
    public PsychoAvailableTimeslotDto getTimeSlotsOfPsycho(@PathVariable String psychoId, @RequestParam LocalDate workingDate) {
        final PsychoWorkday psychoWorkday = workdayService.getWorkdayByPsychoIdAndWorkingDate(psychoId, workingDate);
        return workdayGenerator.generateWorkday(psychoWorkday);
    }

}
