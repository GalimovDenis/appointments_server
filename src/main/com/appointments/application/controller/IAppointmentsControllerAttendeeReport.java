package com.appointments.application.controller;

import java.util.UUID;

import com.appointments.application.dto.AppointmentDTO;

/**
 * Service for appointments attendee;
 * this are the endpoints attendee will reach when;
 */
public interface IAppointmentsControllerAttendeeReport {

	AppointmentDTO report(String organiserName, UUID eventUUID, int sequence);  // get

}
