package com.belstu.thesisproject.generator;

import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.dto.workday.PsychoAvailableTimeslotDto;
import com.belstu.thesisproject.util.RoundUtils;
import com.belstu.thesisproject.util.TimeIntervalUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PsychoWorkdayGeneratorImpl implements PsychoWorkdayGenerator {
    public static final int TIMESLOT_SIZE = 20;

    @Transactional
    @Override
    public PsychoAvailableTimeslotDto generateWorkday(final PsychoWorkday psychoWorkday) {
        final LocalDateTime startDateTime = psychoWorkday.getStartDateTime();
        final LocalDateTime endDateTime = psychoWorkday.getEndDateTime();
        final List<LocalDateTime> psychoTimeslots = generateTimeslotsForBooking(startDateTime, endDateTime);
        final String psychoId = psychoWorkday.getPsychologist().getId();
        return PsychoAvailableTimeslotDto.builder()
                .slots(psychoTimeslots)
                .date(psychoWorkday.getDate())
                .psychoId(psychoId)
                .build();
    }

    private List<LocalDateTime> generateTimeslotsForBooking(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<LocalDateTime> timeslots = new ArrayList<>();

        LocalDateTime current = getNearestDateTimeForGeneratingTimeslots(startDateTime);
        LocalDateTime to = endDateTime.minusMinutes(TIMESLOT_SIZE);
        while (TimeIntervalUtils.dateTimeIsBeforeOrEqual(current, to)) {
            timeslots.add(current);
            current = current.plusMinutes(TIMESLOT_SIZE);
        }
        return timeslots.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    private LocalDateTime getNearestDateTimeForGeneratingTimeslots(LocalDateTime startDateTime) {
        LocalDateTime roundCurrentDateTime = RoundUtils.roundMinToTwenty(LocalDateTime.now());
        return startDateTime.isAfter(roundCurrentDateTime)
                ? startDateTime : roundCurrentDateTime;
    }
}
