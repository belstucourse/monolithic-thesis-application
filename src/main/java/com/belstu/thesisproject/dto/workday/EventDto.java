package com.belstu.thesisproject.dto.workday;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EventDto {
  private String id;
  private String psychologistId;
  private String clientId;
  private LocalDateTime date;
  private Boolean isEnded;
  private Boolean isConfirmed;
  private String reasonForVisit;
}
