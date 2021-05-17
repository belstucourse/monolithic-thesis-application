package com.belstu.thesisproject.dto.workday;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PsychoAvailableTimeslotDto {
  private final LocalDate date;
  private final List<LocalDateTime> slots;
  private final String psychoId;
}
