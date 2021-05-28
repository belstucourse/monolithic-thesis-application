package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.workday.Event;
import com.belstu.thesisproject.domain.workday.Prescription;
import com.belstu.thesisproject.domain.workday.PsychoEventNotes;
import com.belstu.thesisproject.dto.workday.EventDto;
import com.belstu.thesisproject.dto.workday.PrescriptionDto;
import com.belstu.thesisproject.dto.workday.PsychoEventNotesDto;
import com.belstu.thesisproject.mapper.EventMapper;
import com.belstu.thesisproject.mapper.PrescriptionMapper;
import com.belstu.thesisproject.mapper.PsychoEventNotesMapper;
import com.belstu.thesisproject.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.belstu.thesisproject.service.CurrentUserEmailExtractor.getEmailOfCurrentUser;

@RestController
@RequestMapping("/api/event")
@AllArgsConstructor
public class EventController {
    private final EventMapper eventMapper;
    private final EventService eventService;
    private final PrescriptionMapper prescriptionMapper;
    private final PsychoEventNotesMapper psychoEventNotesMapper;

    @GetMapping
    public EventDto getEvent(@RequestParam String clientId, @RequestParam String psychoId, @RequestParam LocalDateTime date) {
        return eventMapper.map(eventService.getByUserIdsAndDate(clientId, psychoId, date));
    }

    @GetMapping("/room/{roomId}")
    public EventDto getEventByRoomID(@PathVariable String roomId) {
        return eventMapper.map(eventService.getByRoomId(roomId));
    }

    @GetMapping("/{clientId}/client")
    public List<EventDto> getClientEvents(@PathVariable String clientId) {
        return eventMapper.mapToDtoList(eventService.getClientEvents(clientId));
    }

    @GetMapping("/{psychoId}")
    public List<EventDto> getEventOfPsycho(@PathVariable String psychoId) {
        return eventMapper.mapToDtoList(eventService.getByPsychoId(psychoId));
    }

    @PostMapping
    public EventDto saveEvent(@RequestBody EventDto eventDto) {
        final String email = getEmailOfCurrentUser();
        final Event event = eventMapper.map(eventDto);
        return eventMapper.map(eventService.save(event, email));
    }

    @PutMapping
    public EventDto updateEvent(@RequestBody EventDto eventDto) {
        final Event event = eventMapper.map(eventDto);
        return eventMapper.map(eventService.update(event));
    }

    @GetMapping("/prescriptions/{eventId}")
    public PrescriptionDto getPrescriptionByEventId(@PathVariable String eventId) {
        final String email = getEmailOfCurrentUser();
        return prescriptionMapper.map(eventService.getPrescriptionByEventId(eventId, email));
    }

    @PostMapping("/prescriptions")
    public PrescriptionDto savePrescription(@RequestBody PrescriptionDto prescriptionDto) {
        final String email = getEmailOfCurrentUser();
        final Prescription prescription = prescriptionMapper.map(prescriptionDto);
        return prescriptionMapper.map(eventService.savePrescription(prescription, email));
    }

    @GetMapping("/notes/{eventId}")
    public PsychoEventNotesDto getEventNotesByEventId(@PathVariable String eventId) {
        final String email = getEmailOfCurrentUser();
        return psychoEventNotesMapper.map(eventService.getEventNotesByEventId(eventId, email));
    }

    @PostMapping("/notes")
    public PsychoEventNotesDto saveEventNotes(@RequestBody PsychoEventNotesDto psychoEventNotesDto) {
        final String email = getEmailOfCurrentUser();
        final PsychoEventNotes psychoEventNotes = psychoEventNotesMapper.map(psychoEventNotesDto);
        return psychoEventNotesMapper.map(eventService.saveEventNotes(psychoEventNotes, email));
    }
}
