package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.domain.workday.Event;
import com.belstu.thesisproject.exception.UserNotFoundException;
import com.belstu.thesisproject.repository.EventRepository;
import com.belstu.thesisproject.service.EventService;
import com.belstu.thesisproject.service.MailSenderService;
import com.belstu.thesisproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static com.belstu.thesisproject.service.message.AppointmentMessageTemplates.APPOINTMENT_CONFIRMED_MESSAGE;
import static com.belstu.thesisproject.service.message.AppointmentMessageTemplates.CONGRATULATION;
import static com.belstu.thesisproject.service.message.AppointmentMessageTemplates.REGISTER_APPOINTMENT_SUCCESSFULLY_MESSAGE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final MailSenderService mailSenderService;
    private final UserService userService;

    @Override
    @Transactional
    public Event save(@NotNull Event event, @NotBlank String email) {
        final User client = userService.getUserById(event.getClient().getId());
        final User psycho = userService.getUserById(event.getPsychologist().getId());
        final String clientEmail = client.getEmail();
        final String psychoEmail = psycho.getEmail();
        validateEmail(clientEmail, psychoEmail, email);
        final Event persistedEvent = eventRepository.save(event);
        if (isEmpty(clientEmail) && isEmpty(psychoEmail)) {
            throw new UserNotFoundException("Invalid email");
        }
        mailSenderService.send(clientEmail,
                CONGRATULATION,
                format(REGISTER_APPOINTMENT_SUCCESSFULLY_MESSAGE, event.getDate().toString()));
        return persistedEvent;
    }

    @Override
    @Transactional
    public Event update(@NotNull Event event) {
        final User client = userService.getUserById(event.getClient().getId());
        final User psycho = userService.getUserById(event.getPsychologist().getId());
        final String clientEmail = client.getEmail();
        final String psychoEmail = psycho.getEmail();
        final Event persistedEvent = eventRepository.save(event);
        if (isEmpty(clientEmail) && isEmpty(psychoEmail)) {
            throw new UserNotFoundException("Invalid email");
        }
        if (persistedEvent.getIsConfirmed() == TRUE) {
            mailSenderService.send(clientEmail,
                    CONGRATULATION,
                    format(APPOINTMENT_CONFIRMED_MESSAGE, psycho.getFirstName(), event.getDate().toString(), persistedEvent.getRoomId()));
        }
        return persistedEvent;
    }

    @Override
    public List<Event> getByPsychoId(String psychoId) {
        return eventRepository.findByPsychologistId(psychoId);
    }

    @Override
    public List<Event> getClientEvents(String clientId) {
        return eventRepository.findByClientId(clientId);
    }

    @Override
    public Event getByRoomId(String roomId) {
        return eventRepository.findByRoomId(roomId).orElseThrow(()->new UserNotFoundException(roomId));
    }

    @Override
    public Event getByUserIdsAndDate(@NotNull String clientId, @NotNull String psychoId, @NotNull LocalDateTime date) {
        return eventRepository.findByClientIdAndPsychologistIdAndDate(clientId, psychoId, date).orElseThrow(() -> new UserNotFoundException("Event not found"));
    }

    private void validateEmail(String clientEmail, String psychoEmail, String currentUserEmail) {
        if (!clientEmail.equals(currentUserEmail) && !psychoEmail.equals(currentUserEmail)) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
