package com.appointments.controller;

import java.util.UUID;

/**
 * Service for appointments attendee;
 * this are the endpoints attendee will reach when;
 */
public interface IAppointmentsControllerAttendeeComplete {
	
    Boolean eventComplete(String organiserName, UUID eventUUID);  // post


}
