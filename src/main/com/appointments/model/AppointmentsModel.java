package com.appointments.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.appointments.application.dto.AppointmentDTO;
import com.appointments.application.dto.IAppointmentDTO;


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
	 * Attendee tries to create event; returns that the event was put into queue; 
	 */
	public Boolean register(IAppointmentDTO appRequest	) {
		
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
	 * Attendee checks if his event was created; 
	 */
	@Override
	public AppointmentDTO answer(String organizerName, UUID uid) {
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		displayMapByOrganizer(organizerName);
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			
			AppointmentDTO request = (AppointmentDTO) entry.getValue();
			
			if (request.isResponded() == true) return request;
			
		}
		
		return null;
	}



	/**
	 * Organiser gets events that he has to create; 
	 */
	@Override
	public AppointmentDTO pendingTo(String organizerName) {
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 

		displayMapByOrganizer(organizerName);
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			AppointmentDTO request = (AppointmentDTO) entry.getValue();
			if (request.isResponded() == false) return request;
		}
		
		return null; 
	
	}
	

	/**
	 * Organiser reports on event status;
	 */
	public Boolean report(IAppointmentDTO appEvent) { // need generic dto or split reports; 
		
		String organizerName = appEvent.getOrganizer();
		
		UUID UUID = appEvent.getRequestId();
		
		if (!appointmentsRegister.containsKey(organizerName)) { appointmentsRegister.put(organizerName, new TreeMap<UUID, IAppointmentDTO>());}
		
		appointmentsRegister.get(organizerName).put(UUID, appEvent);
		
		displayMapByOrganizer(organizerName);
				
		return true; // unknown how to reflect successful report; 
		
	}



	
	private void displayMapByOrganizer(String organizerName) {
		
		for(Entry<UUID, IAppointmentDTO> entry : appointmentsRegister.get(organizerName).entrySet()){
			System.out.println("UUID "+entry.getKey()+" entry "+entry.getValue().toString());
		}
		
	}
	
	@SuppressWarnings("unused")
	private void displayMap() {
		//TODO:full map display
	}






}
