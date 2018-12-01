package com.appointments.application.dto;

import java.util.UUID;

import com.appointments.util.date.range.IDateRange;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * dto for appointment creation; appointment is event with Organizer and Attendee; 
 *
 */
public class AppointmentDTO extends TimedAppointmentDTO implements IAppointmentDTO {
	
	@JsonCreator
	public AppointmentDTO(@JsonProperty("requestId") UUID requestId, 
			@JsonProperty("range") IDateRange range,
			@JsonProperty("requestType") RequestType requestType) {
		super(requestId, range, requestType);
	}
	
}
