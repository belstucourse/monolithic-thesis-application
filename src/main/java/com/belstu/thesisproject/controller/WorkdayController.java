package com.belstu.thesisproject.controller;

import static org.springframework.http.HttpStatus.OK;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.dto.workday.PsychoAvailableTimeslotDto;
import com.belstu.thesisproject.dto.workday.PsychoWorkdayDto;
import com.belstu.thesisproject.generator.PsychoWorkdayGenerator;
import com.belstu.thesisproject.mapper.WorkdayMapper;
import com.belstu.thesisproject.service.WorkdayService;
import java.time.LocalDate;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timeslot")
@AllArgsConstructor
public class WorkdayController {
  private final WorkdayService workdayService;
  private final PsychoWorkdayGenerator workdayGenerator;
  private final WorkdayMapper workdayMapper;

  @GetMapping("/{psychoId}")
  public PsychoAvailableTimeslotDto getTimeSlotsOfPsycho(
      @PathVariable String psychoId, @RequestParam LocalDate workingDate) {
    final PsychoWorkday psychoWorkday =
        workdayService.getWorkdayByPsychoIdAndWorkingDate(psychoId, workingDate);
    return workdayGenerator.generateWorkday(psychoWorkday);
  }

  @PostMapping
  public PsychoWorkdayDto saveWorkdayOfPsychologist(
      @Valid @RequestBody PsychoWorkdayDto psychoWorkdayDto) {
    final PsychoWorkday workday = workdayMapper.map(psychoWorkdayDto);
    return workdayMapper.map(workdayService.save(workday));
  }

  @DeleteMapping("/{psychoId}")
  @ResponseStatus(OK)
  public void saveWorkdayOfPsychologist(
      @PathVariable String psychoId, @RequestParam LocalDate date) {
    workdayService.delete(psychoId, date);
  }
}
