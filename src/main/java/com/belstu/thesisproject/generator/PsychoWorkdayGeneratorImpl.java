package com.belstu.thesisproject.generator;

import com.belstu.thesisproject.domain.user.Psychologist;
import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.dto.workday.PsychoAvailableTimeslotDto;
import com.belstu.thesisproject.service.EventService;
import com.belstu.thesisproject.util.RoundUtils;
import com.belstu.thesisproject.util.TimeIntervalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.belstu.thesisproject.generator.TimeslotParams.TIMESLOT_SIZE;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@RequiredArgsConstructor
public class PsychoWorkdayGeneratorImpl implements PsychoWorkdayGenerator {
    private final EventService eventService;

    @Transactional
    @Override
    public PsychoAvailableTimeslotDto generateWorkday(final PsychoWorkday psychoWorkday) {
        final LocalDateTime startDateTime = psychoWorkday.getStartDateTime();
        final LocalDateTime endDateTime = psychoWorkday.getEndDateTime();
        final Psychologist psychologist = psychoWorkday.getPsychologist();
        final List<LocalDateTime> psychoTimeslots =
                generateTimeslotsForBooking(startDateTime, endDateTime, psychologist);
        final String psychoId = getPsychoId(psychologist);
        return PsychoAvailableTimeslotDto.builder()
                .slots(psychoTimeslots)
                .date(psychoWorkday.getDate())
                .psychoId(psychoId)
                .build();
    }

    private String getPsychoId(Psychologist psychologist) {
        if (nonNull(psychologist)) {
            return psychologist.getId();
        }
        return EMPTY;
    }

    private List<LocalDateTime> generateTimeslotsForBooking(
            LocalDateTime startDateTime, LocalDateTime endDateTime, Psychologist psychologist) {
        if (isNull(startDateTime) || isNull(endDateTime)) {
            return emptyList();
        }
        List<LocalDateTime> timeslots = new ArrayList<>();

        LocalDateTime current = getNearestDateTimeForGeneratingTimeslots(startDateTime);
        LocalDateTime to = endDateTime.minusMinutes(TIMESLOT_SIZE);
        while (TimeIntervalUtils.dateTimeIsBeforeOrEqual(current, to)) {
            if (eventService.getByScheduledTimeAndPsychoId(current, getPsychoId(psychologist)).isPresent()) {
                timeslots.add(current);
            }
            current = current.plusMinutes(TIMESLOT_SIZE);
        }
        return timeslots.stream().sorted().collect(Collectors.toList());
    }

    private LocalDateTime getNearestDateTimeForGeneratingTimeslots(LocalDateTime startDateTime) {
        LocalDateTime roundCurrentDateTime = RoundUtils.roundMinToTwenty(LocalDateTime.now());
        return startDateTime.isAfter(roundCurrentDateTime) ? startDateTime : roundCurrentDateTime;
    }
}
