package com.belstu.thesisproject.dto.workday;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetailedEventDto {
    private String id;
    private String psychologistId;
    private String clientId;
    private LocalDateTime date;
    private Boolean isEnded;
    private String reasonForVisit;
    private String roomId;
    private String feedback;
    private Boolean isConfirmed;
    private String psychologistName;
    private String ClientName;
    private boolean isRepeated;
    private PrescriptionDto prescription;
    private PsychoEventNotesDto psychoEventNotes;
}
