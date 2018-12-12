package com.appointments.application.repository;

import java.util.Map;
import java.util.UUID;

import com.appointments.application.dto.IAppointmentDTO;
import com.appointments.application.dto.RequestType;
import com.appointments.application.entity.IRequestKey;
import com.appointments.application.entity.RequestStatus;

public interface IAppointmentsRepo {
	
	public void clear();
	
	/**
	 * Puts a request into repository
	 */
	public boolean putIfAbsent(String organizerName, IAppointmentDTO appDTO);
	
	/**
	 * Getting a map of requests related to concrete organizer;
	 * @param organizerName
	 * @return
	 */
	public Map<IRequestKey, IAppointmentDTO> getOrganizerRequests(String organizerName);
	
	
	public IAppointmentDTO getRequest(String orgName, RequestType requestType, RequestStatus requestStatus, UUID eventID, int sequence);

//	public Iterable<IAppointmentDTO> getIterable(String orgName, RequestType requestType, RequestStatus requestStatus, UUID eventID, int sequence);

	/**
	 * Deletes all "Complete" requests
	 */
	public void deleteCompleteRequests();
	
	/**
	 * Deletes all "Complete" request for concrete organizer;
	 * @param organizerName
	 */
	public void deleteCompleteRequests(String organizerName);
	
}
