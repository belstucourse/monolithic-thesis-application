package com.belstu.thesisproject.repository;

import com.belstu.thesisproject.domain.workday.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String> {
    Optional<Event> findByClientIdAndPsychologistIdAndDate(String clientId, String psychoId, LocalDateTime date);
}
