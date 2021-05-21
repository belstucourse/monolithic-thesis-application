package com.belstu.thesisproject.service.message;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AppointmentMessageTemplates {
    public static final String REGISTER_APPOINTMENT_SUCCESSFULLY_MESSAGE = "Appointment was registered on: %s";
    public static final String APPOINTMENT_CONFIRMED_MESSAGE = "Appointment confirmed by psychologist %s. Scheduled time: %s. Link on the video room: %s";
    public static final String APPOINTMENT_REJECTED_MESSAGE = "Appointment rejected by psychologist %s which was scheduled on %s";
    public static final String APPOINTMENT_SCHEDULING_REMINDER_MESSAGE = "Appointment will in 10 minutes with Dr. %s. Link to the room: %s";
    public static final String CONGRATULATION = "CONGRATULATION";

}
