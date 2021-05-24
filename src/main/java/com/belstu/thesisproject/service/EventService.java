package com.belstu.thesisproject.service;

import com.belstu.thesisproject.domain.workday.Event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Event save(@NotNull Event event, @NotBlank String email);

    Event getByUserIdsAndDate(@NotNull final String userEmail, @NotNull final String psychoId, LocalDateTime date);

    Event update(@NotNull Event event);

    List<Event> getByPsychoId(String psychoId);

    List<Event> getClientEvents(String clientId);

    Event getByRoomId(String roomId);
}
