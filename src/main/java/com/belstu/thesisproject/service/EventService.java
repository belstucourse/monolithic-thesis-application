package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.workday.Event;
import com.belstu.thesisproject.domain.workday.Prescription;
import com.belstu.thesisproject.domain.workday.PsychoEventNotes;
import com.belstu.thesisproject.dto.workday.PsychoEventNotesDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventService {
    Event save(@NotNull Event event, @NotBlank String email);

    Optional<Event> getByScheduledTimeAndPsychoId(LocalDateTime date, String psychoId);

    Event getByUserIdsAndDate(@NotNull final String userEmail, @NotNull final String psychoId, LocalDateTime date);

    Event update(@NotNull Event event);

    List<Event> getByPsychoId(String psychoId);

    List<Event> getClientEvents(String clientId);

    Event getByRoomId(String roomId);

    Prescription savePrescription(Prescription prescription, String email);

    Prescription getPrescriptionByEventId(String eventId, String email);

    PsychoEventNotes getEventNotesByEventId(String eventId, String email);

    PsychoEventNotes saveEventNotes(PsychoEventNotes psychoEventNotes, String email);
}
