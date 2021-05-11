package com.belstu.thesisproject.dto.workday;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {
    private String id;
    private String psychologistId;
    private String clientId;
    private LocalDateTime date;
    private Boolean isEnded;
    private Boolean isConfirmed;
}
