package com.appointments.application.dto;

import java.util.UUID;

import com.appointments.util.daterange.IDateRange;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppointmentRead extends TimedAppointmentDTO implements IAppointmentDTO {
	
	final RequestType requestType = RequestType.READ;
	
	@JsonCreator
	public AppointmentRead(@JsonProperty("requestId") UUID requestId, @JsonProperty("range") IDateRange range) {
		super(requestId, range);
	}
	
	@Override
	public RequestType getRequestType() {
		return requestType;
	} 
	
}
