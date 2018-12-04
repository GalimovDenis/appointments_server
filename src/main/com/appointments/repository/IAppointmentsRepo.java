package com.appointments.repository;

import java.util.Map;
import java.util.UUID;

import com.appointments.dto.IAppointmentDTO;

public interface IAppointmentsRepo {
	
	/**
	 * Puts a request into repository
	 */
	public IAppointmentDTO putIfAbsent(String organizerName, IAppointmentDTO appDTO);
	
	/**
	 * Getting a map of requests related to concrete organizer;
	 * @param organizerName
	 * @return
	 */
	public Map<UUID, IAppointmentDTO> getOrganizerRequests(String organizerName);
	

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
