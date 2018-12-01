package com.appointments.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.appointments.application.dto.AppointmentCreate;
import com.appointments.application.dto.AppointmentDelete;
import com.appointments.application.dto.AppointmentRead;
import com.appointments.application.dto.AppointmentUpdate;
import com.appointments.application.dto.IAppointmentDTO;
import com.appointments.application.dto.RequestType;


/**
 * Mediator between organizer and attendee; 
 */
@Component("AppointmentsModel")
public class AppointmentsModel implements IAppointmentsModel {

	/**
	 * Ledger for storing pending events to create by user name; attendees add data into it
	 */
	private Map<String, Map<UUID, IAppointmentDTO>> appointmentsRegister = new ConcurrentHashMap<String, Map<UUID, IAppointmentDTO>>();
	
	
	public AppointmentsModel() {
		super();
	}
	
	/**
	 * Registering any appointment request in the ledger
	 * @param appRequest
	 * @return
	 */
	private Boolean register(IAppointmentDTO appRequest	) {
		
		String organizerName = appRequest.getOrganizer();

		if (!appointmentsRegister.containsKey(organizerName)) {
			
			appointmentsRegister.put(organizerName, new TreeMap<UUID, IAppointmentDTO>());
				
		}
		
		appRequest.setRegistered(true);
		
		appointmentsRegister.get(organizerName).put(appRequest.getRequestId(), appRequest);
	
		displayMapByOrganizer(organizerName);
		
		return true;
	}
	
	/**
	 * Getting a map of requests related to concrete organizer;
	 * @param organizerName
	 * @return
	 */
	private Map<UUID, IAppointmentDTO> getOrganizerRequests(String organizerName) {
		if (!appointmentsRegister.containsKey(organizerName)) {
			
			appointmentsRegister.put(organizerName, new TreeMap<UUID, IAppointmentDTO>());
				
		}
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = appointmentsRegister.get(organizerName);
		return mapOfPendingCreations;
	}

	/**
	 * Attendee tries to create event; returns that the event was put into queue; 
	 */
	@Override
	public Boolean registerCreate(AppointmentCreate appRequest) {	
		
		System.out.println(appRequest.getDateRange() == null);
	
		return register(appRequest);

	}
	
	/**
	 * Attendee tries to read event; returns that the event was put into queue; 
	 */
	@Override
	public Boolean registerRead(AppointmentRead appRequest) {	
		
		return register(appRequest);

	}
	/**
	 * Attendee tries to update event; returns that the event was put into queue; 
	 */
	@Override
	public Boolean registerUpdate(AppointmentUpdate appRequest) {	
		
		return register(appRequest);

	}
	/**
	 * Attendee tries to delete event; returns that the event was put into queue; 
	 */
	@Override
	public Boolean registerDelete(AppointmentDelete appRequest) {	
		
		return register(appRequest);

	}

	
	/**
	 * Attendee checks if his event was created; 
	 */
	@Override
	public AppointmentCreate answeredCreate(String organizerName, UUID uid) {
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		displayMapByOrganizer(organizerName);
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			if (entry.getValue().getRequestType() != RequestType.CREATE) continue; 
			AppointmentCreate request = (AppointmentCreate) entry.getValue();
			if (request.isResponded() == true) return request;
		}
		
		return null;
	}


	/**
	 * Attendee checks if his event was read; 
	 */	
	@Override
	public AppointmentRead answeredRead(String organizerName, UUID uid) {
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			if (entry.getValue().getRequestType() != RequestType.READ) continue; 
			AppointmentRead request = (AppointmentRead) entry.getValue();
			if (request.isResponded() == true) return request;
		}
		
		return null;
	}


	/**
	 * Attendee checks if his event was updated; 
	 */
	@Override
	public AppointmentUpdate answeredUpdate(String organizerName, UUID uid) {
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			if (entry.getValue().getRequestType() != RequestType.UPDATE) continue; 
			AppointmentUpdate request = (AppointmentUpdate) entry.getValue();
			if (request.isResponded() == true) return request;
		}
		
		return null;
	}


	/**
	 * Attendee checks if his event was deleted; 
	 */
	@Override
	public AppointmentDelete answeredDelete(String organizerName, UUID uid) {
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			if (entry.getValue().getRequestType() != RequestType.DELETE) continue; 
			AppointmentDelete request = (AppointmentDelete) entry.getValue();
			if (request.isResponded() == true) return request;
		}
		
		return null;
	}



	/**
	 * Organiser gets events that he has to create; 
	 */
	@Override
	public AppointmentCreate pendingToCreate(String organizerName) {
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 

		displayMapByOrganizer(organizerName);
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			if (entry.getValue().getRequestType() != RequestType.CREATE) continue; 
			AppointmentCreate request = (AppointmentCreate) entry.getValue();
			if (request.isResponded() == false) return request;
		}
		
		return null; 
	
	}
	

	/**
	 * Organiser gets a request on event status;
	 */
	@Override
	public AppointmentRead pendingToRead(String organizerName) {
		
        Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			if (entry.getValue().getRequestType() != RequestType.READ) continue; 
			AppointmentRead request = (AppointmentRead) entry.getValue();
			if (request.isResponded() == false) return request;
		}
		
		return null; 
	}

	


	/**
	 * Organiser gets a request on event status;
	 */
	@Override
	public AppointmentUpdate pendingToUpdate(String organizerName) {
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			if (entry.getValue().getRequestType() != RequestType.UPDATE) continue; 
			AppointmentUpdate request = (AppointmentUpdate) entry.getValue();
			if (request.isResponded() == false) return request;
		}
		
		return null; 
	}

	/**
	 * Organiser gets a request on event status;
	 */
	@Override
	public AppointmentDelete pendingToDelete(String organizerName) {

		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			if (entry.getValue().getRequestType() != RequestType.DELETE) continue; 
			AppointmentDelete request = (AppointmentDelete) entry.getValue();
			if (request.isResponded() == false) return request;
		}
		
		return null; 
	}

	/**
	 * Organiser reports on event status;
	 */
	@Override
	public Boolean reportCreate(AppointmentCreate appEvent) { // need generic dto or split reports; 
		
		String organizerName = appEvent.getOrganizer();
		
		UUID UUID = appEvent.getRequestId();
		
		if (!appointmentsRegister.containsKey(organizerName)) { appointmentsRegister.put(organizerName, new TreeMap<UUID, IAppointmentDTO>());}
		
		appointmentsRegister.get(organizerName).put(UUID, appEvent);
		
		displayMapByOrganizer(organizerName);
				
		return true; // unknown how to reflect successful report; 
		
	}

	/**
	 * Organiser reports on event status;
	 */
	@Override
	public Boolean reportRead(AppointmentRead appEvent) { // need generic dto or split reports; 
		
		String organizerName = appEvent.getOrganizer();
		
		UUID UUID = appEvent.getRequestId();
		
		if (!appointmentsRegister.containsKey(organizerName)) { appointmentsRegister.put(organizerName, new TreeMap<UUID, IAppointmentDTO>());}
		
		appointmentsRegister.get(organizerName).put(UUID, appEvent);
				
		return true; // unknown how to reflect successful report; 
		
	}

	/**
	 * Organiser reports on event status;
	 */
	@Override
	public Boolean reportUpdate(AppointmentUpdate appEvent) { // need generic dto or split reports; 
		
		String organizerName = appEvent.getOrganizer();
		
		UUID UUID = appEvent.getRequestId();
		
		if (!appointmentsRegister.containsKey(organizerName)) { appointmentsRegister.put(organizerName, new TreeMap<UUID, IAppointmentDTO>());}
		
		appointmentsRegister.get(organizerName).put(UUID, appEvent);
				
		return true; // unknown how to reflect successful report; 
		
	}

	/**
	 * Organiser reports on event status;
	 */
	@Override
	public Boolean reportDelete(AppointmentDelete appEvent) { // need generic dto or split reports; 
		
		String organizerName = appEvent.getOrganizer();
		
		UUID UUID = appEvent.getRequestId();
		
		if (!appointmentsRegister.containsKey(organizerName)) { appointmentsRegister.put(organizerName, new TreeMap<UUID, IAppointmentDTO>());}
		
		appointmentsRegister.get(organizerName).put(UUID, appEvent);
				
		return true; // unknown how to reflect successful report; 
		
	}




	
	private void displayMapByOrganizer(String organizerName) {
		
		for(Entry<UUID, IAppointmentDTO> entry : appointmentsRegister.get(organizerName).entrySet()){
			System.out.println("UUID "+entry.getKey()+" entry "+entry.getValue().toString());
		}
		
	}
	
	private void displayMap() {
		//TODO:full map display
	}

}
