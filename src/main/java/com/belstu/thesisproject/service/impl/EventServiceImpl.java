package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.workday.Event;
import com.belstu.thesisproject.exception.UserNotFoundException;
import com.belstu.thesisproject.repository.EventRepository;
import com.belstu.thesisproject.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event getByUserIdsAndDate(@NotNull String clientId, @NotNull String psychoId, @NotNull LocalDateTime date) {
        return eventRepository.findByClientIdAndPsychologistIdAndDate(clientId, psychoId, date).orElseThrow(() -> new UserNotFoundException("Event not found"));
    }
}
