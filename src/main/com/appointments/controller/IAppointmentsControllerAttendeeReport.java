package com.appointments.controller;

import java.util.UUID;

import com.appointments.dto.AppointmentDTO;

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
