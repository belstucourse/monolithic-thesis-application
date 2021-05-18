package com.belstu.thesisproject.generator;

import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.dto.workday.PsychoAvailableTimeslotDto;
import com.belstu.thesisproject.util.RoundUtils;
import com.belstu.thesisproject.util.TimeIntervalUtils;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class PsychoWorkdayGeneratorImpl implements PsychoWorkdayGenerator {
  public static final int TIMESLOT_SIZE = 40;

  @Transactional
  @Override
  public PsychoAvailableTimeslotDto generateWorkday(final PsychoWorkday psychoWorkday) {
    final LocalDateTime startDateTime = psychoWorkday.getStartDateTime();
    final LocalDateTime endDateTime = psychoWorkday.getEndDateTime();
    final List<LocalDateTime> psychoTimeslots =
        generateTimeslotsForBooking(startDateTime, endDateTime);
    final String psychoId = getPsychoId(psychoWorkday.getPsychologist());
    return PsychoAvailableTimeslotDto.builder()
        .slots(psychoTimeslots)
        .date(psychoWorkday.getDate())
        .psychoId(psychoId)
        .build();
  }

  private String getPsychoId(Psychologist psychologist) {
    if(nonNull(psychologist))
    {
      return psychologist.getId();
    }
    return EMPTY;
  }

  private List<LocalDateTime> generateTimeslotsForBooking(
      LocalDateTime startDateTime, LocalDateTime endDateTime) {
    if(isNull(startDateTime) || isNull(endDateTime))
    {
      return emptyList();
    }
    List<LocalDateTime> timeslots = new ArrayList<>();

    LocalDateTime current = getNearestDateTimeForGeneratingTimeslots(startDateTime);
    LocalDateTime to = endDateTime.minusMinutes(TIMESLOT_SIZE);
    while (TimeIntervalUtils.dateTimeIsBeforeOrEqual(current, to)) {
      timeslots.add(current);
      current = current.plusMinutes(TIMESLOT_SIZE);
    }
    return timeslots.stream().sorted().collect(Collectors.toList());
  }

  private LocalDateTime getNearestDateTimeForGeneratingTimeslots(LocalDateTime startDateTime) {
    LocalDateTime roundCurrentDateTime = RoundUtils.roundMinToTwenty(LocalDateTime.now());
    return startDateTime.isAfter(roundCurrentDateTime) ? startDateTime : roundCurrentDateTime;
  }
}
