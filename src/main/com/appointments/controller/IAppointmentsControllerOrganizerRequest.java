package com.appointments.controller;

import com.appointments.application.dto.AppointmentDTO;

/**
 * Services organizer will reach;
 *
 */
public interface IAppointmentsControllerOrganizerRequest {

    AppointmentDTO IncomingNewEvent(String organizerName); // get

    AppointmentDTO IncomingReport(String organizerName); // get

    AppointmentDTO IncomingChangeEvent(String organizerName); // get

    AppointmentDTO IncomingDeleteEvent(String organizerName); // get

	
}
