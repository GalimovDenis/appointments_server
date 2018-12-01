package com.appointments.controller;

import com.appointments.application.dto.AppointmentDTO;

import java.util.UUID;

/**
 * Service for appointments attendee;
 * this are the endpoints attendee will reach when;
 */
public interface IAppointmentsControllerAttendeeReport {
    AppointmentDTO createEvent(String organiserName, UUID eventUUID);  // get

    AppointmentDTO readEvent(String organiserName, UUID eventUUID); // get

    AppointmentDTO updateEvent(String organiserName, UUID eventUUID);// get

    AppointmentDTO deleteEvent(String organiserName, UUID eventUUID);    // get

}
