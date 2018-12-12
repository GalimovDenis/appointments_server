package com.appointments.application.entity;

import java.util.UUID;

import com.appointments.application.dto.IAppointmentDTO;
import com.appointments.application.dto.RequestType;

/**
 * Data for finding a request in the repo;
 *
 */
public interface IRequestKey extends Comparable<IRequestKey> {
	
	public static IRequestKey create(IAppointmentDTO appDTO) {
		return new RequestKey(RequestStatus.create(appDTO), appDTO.getSequence(), appDTO.getRequestType(), UUID.fromString(appDTO.getEventId()));
	}
	
	public RequestStatus getStatus();
	
	public UUID getEventID();
	
	public RequestType getRequestType();
	
	public int getSequence();
	
}
