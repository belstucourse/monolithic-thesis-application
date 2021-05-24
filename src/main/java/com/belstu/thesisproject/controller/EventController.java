package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.workday.Event;
import com.belstu.thesisproject.dto.workday.EventDto;
import com.belstu.thesisproject.mapper.EventMapper;
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
}
