package com.belstu.thesisproject.dto.workday;

import com.belstu.thesisproject.domain.workday.Prescription;
import lombok.Data;

@Data
public class PsychoEventNotesDto {
    private String id;
    private String symptoms;
    private String eventId;
    private String specialNotes;
}
