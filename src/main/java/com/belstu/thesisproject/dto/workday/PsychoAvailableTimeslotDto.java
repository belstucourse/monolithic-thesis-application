package com.belstu.thesisproject.dto.workday;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PsychoAvailableTimeslotDto {
    private final LocalDate date;
    private final List<LocalDateTime> slots;
    private final String psychoId;
}
