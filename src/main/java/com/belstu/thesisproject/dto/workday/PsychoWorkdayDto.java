package com.belstu.thesisproject.dto.workday;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PsychoWorkdayDto {
    private String id;

    private String psychologistId;

    private LocalDate date;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
}
