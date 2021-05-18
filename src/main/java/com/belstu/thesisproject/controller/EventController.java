package com.belstu.thesisproject.controller;

import com.belstu.thesisproject.domain.workday.Event;
import com.belstu.thesisproject.dto.workday.EventDto;
import com.belstu.thesisproject.mapper.EventMapper;
import com.belstu.thesisproject.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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

    @PostMapping
    public EventDto saveEvent(EventDto eventDto) {
        final Event event = eventMapper.map(eventDto);
        return eventMapper.map(eventService.save(event));
    }
}
