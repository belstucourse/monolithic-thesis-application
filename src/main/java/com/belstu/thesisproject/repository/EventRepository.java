package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.workday.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String> {
    Optional<Event> findByClientIdAndPsychologistIdAndDate(String clientId, String psychoId, LocalDateTime date);
    List<Event> findByPsychologistId(String psychoId);
    List<Event> findByClientId(String clientId);
    Optional<Event> findByRoomId(String roomId);
    Optional<Event> findByDateAndPsychologistId(LocalDateTime date, String psychoId);
}
