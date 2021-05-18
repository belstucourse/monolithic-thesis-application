package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.workday.Event;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public interface EventService {
    Event save(Event event);

    Event getByUserIdsAndDate(@NotNull final String clientId, @NotNull final String psychoId, LocalDateTime date);
}
