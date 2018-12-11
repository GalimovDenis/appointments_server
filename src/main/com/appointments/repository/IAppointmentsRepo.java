package com.appointments.repository;

import java.util.Map;

import com.appointments.application.entity.RequestKey;
import com.appointments.dto.IAppointmentDTO;

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
	public Map<RequestKey, IAppointmentDTO> getOrganizerRequests(String organizerName);
	

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
