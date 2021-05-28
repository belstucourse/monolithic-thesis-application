package com.belstu.thesisproject.service.impl;

import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.domain.workday.Event;
import com.belstu.thesisproject.domain.workday.Prescription;
import com.belstu.thesisproject.domain.workday.PsychoEventNotes;
import com.belstu.thesisproject.exception.NotFoundException;
import com.belstu.thesisproject.repository.EventRepository;
import com.belstu.thesisproject.repository.PrescriptionRepository;
import com.belstu.thesisproject.repository.PsychoEventNotesRepository;
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
import java.util.Optional;

import static com.belstu.thesisproject.service.message.AppointmentMessageTemplates.APPOINTMENT_REJECTED_MESSAGE;
import static com.belstu.thesisproject.service.message.AppointmentMessageTemplates.CONGRATULATION;
import static com.belstu.thesisproject.service.message.AppointmentMessageTemplates.REGISTER_APPOINTMENT_SUCCESSFULLY_MESSAGE;
import static java.lang.Boolean.FALSE;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final MailSenderService mailSenderService;
    private final UserService userService;
    private final PrescriptionRepository prescriptionRepository;
    private final PsychoEventNotesRepository psychoEventNotesRepository;

    @Override
    @Transactional
    public Event save(@NotNull Event event, @NotBlank String email) {
        final User client = userService.getUserById(event.getClient().getId());
        final User psycho = userService.getUserById(event.getPsychologist().getId());
        final String clientEmail = client.getEmail();
        final String psychoEmail = psycho.getEmail();
        validateEmail(clientEmail, psychoEmail, email);
        final Event persistedEvent = eventRepository.save(event);
        if (isEmpty(clientEmail) || isEmpty(psychoEmail)) {
            throw new NotFoundException("Invalid email");
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
        if (isEmpty(clientEmail) || isEmpty(psychoEmail)) {
            throw new NotFoundException("Invalid email");
        }
        if (persistedEvent.getIsConfirmed() == FALSE) {
            mailSenderService.send(clientEmail,
                    CONGRATULATION,
                    format(APPOINTMENT_REJECTED_MESSAGE, psycho.getFirstName(), event.getDate().toString()));
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
        return eventRepository.findByRoomId(roomId).orElseThrow(() -> new NotFoundException(roomId));
    }

    @Override
    public Prescription savePrescription(Prescription prescription, String email) {
        final Event event = eventRepository.findById(prescription.getEvent().getId()).orElseThrow(() -> new NotFoundException("Event not found"));
        final User psycho = userService.getUserById(event.getPsychologist().getId());
        validatePsychoEmail(psycho, email);
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription getPrescriptionByEventId(String eventId, String email) {
        final Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found"));
        final User psycho = userService.getUserById(event.getPsychologist().getId());
        final User client = userService.getUserById(event.getClient().getId());
        validateEmail(client.getEmail(), psycho.getEmail(), email);
        return prescriptionRepository.findByEventId(eventId).orElseThrow(() -> new NotFoundException("Event not found"));
    }

    @Override
    public PsychoEventNotes getEventNotesByEventId(String eventId, String email) {
        return psychoEventNotesRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found"));
    }

    @Override
    public PsychoEventNotes saveEventNotes(PsychoEventNotes psychoEventNotes, String email) {
        final Event event = eventRepository.findById(psychoEventNotes.getEvent().getId()).orElseThrow(() -> new NotFoundException("Event not found"));
        final User psycho = userService.getUserById(event.getPsychologist().getId());
        validatePsychoEmail(psycho, email);
        return psychoEventNotesRepository.save(psychoEventNotes);
    }

    private void validatePsychoEmail(User psycho, String email) {
        if (!psycho.getEmail().equals(email)) {
            throw new AccessDeniedException("User account not type of psycho");
        }
    }

    @Override
    public Optional<Event> getByScheduledTimeAndPsychoId(LocalDateTime date, String psychoId) {
        return eventRepository.findByDateAndPsychologistId(date, psychoId);
    }

    @Override
    public Event getByUserIdsAndDate(@NotNull String clientId, @NotNull String psychoId, @NotNull LocalDateTime date) {
        return eventRepository.findByClientIdAndPsychologistIdAndDate(clientId, psychoId, date).orElseThrow(() -> new NotFoundException("Event not found"));
    }

    private void validateEmail(String clientEmail, String psychoEmail, String currentUserEmail) {
        if (!clientEmail.equals(currentUserEmail) && !psychoEmail.equals(currentUserEmail)) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
