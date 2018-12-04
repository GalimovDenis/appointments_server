package com.appointments.dto;

import java.util.UUID;

import com.appointments.util.date.range.IDateRange;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({ @JsonSubTypes.Type(value = AppointmentDTO.class, name = "AppointmentDTO")})
public interface IAppointmentDTO {
	
	public RequestType getRequestType();
	
	public UUID getRequestId();
	
	public String getEventId();
		
	public String getOrganizer();
	
	public String getAttendee();
	
	public IDateRange getDateRange();
	
	public void setEventId(String eventId);
	
	public void setOrganizer(String organizer);
	
	public void setAttendee(String attendee);
	
	
	public boolean isRegistered();
	
	public boolean isResponded();
	
	public boolean isComplete();
	
	public void setRegistered(boolean registered);
	
	public void setResponded(boolean responded);
	
	public void setComplete(boolean complete);
	
}
