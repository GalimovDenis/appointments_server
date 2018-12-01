package com.appointments.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.appointments.application.dto.AppointmentDTO;
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
	

	
	private void displayMapByOrganizer(String organizerName) {
		
		for(Entry<UUID, IAppointmentDTO> entry : appointmentsRegister.get(organizerName).entrySet()){
			System.out.println("UUID "+entry.getKey()+" entry "+entry.getValue().toString());
		}
		
	}
	
	@SuppressWarnings("unused")
	private void displayMap() {
		//TODO:full map display
	}


	
	/**
	 * Attendee tries to modify event; returns that the modification request was put into queue; 
	 */
	public Boolean register(IAppointmentDTO appRequest	) {
		
		String organizerName = appRequest.getOrganizer();

		if (!appointmentsRegister.containsKey(organizerName)) {
			
			appointmentsRegister.put(organizerName, new TreeMap<UUID, IAppointmentDTO>());
				
		}
		
		appRequest.setRegistered(true);
		
		appointmentsRegister.get(organizerName).put(appRequest.getRequestId(), appRequest);
		
		return true;
	}
	

	/**
	 * Organizer gets events that he has to modify; 
	 */
	@Override
	public AppointmentDTO pendingTo(String organizerName, RequestType type) {
			
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 

		displayMapByOrganizer(organizerName);
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			AppointmentDTO request = (AppointmentDTO) entry.getValue();
			if (!request.getRequestType().equals(type)) continue; 
			if (request.isResponded() == false) return request;
		}
		
		return null; 
	
	}


	/**
	 * Organizer reports on event status;
	 */
	public Boolean report(IAppointmentDTO appEvent) { // need generic dto or split reports; 
		
		String organizerName = appEvent.getOrganizer();
		
		UUID UUID = appEvent.getRequestId();
		
		if (!appointmentsRegister.containsKey(organizerName)) { appointmentsRegister.put(organizerName, new TreeMap<UUID, IAppointmentDTO>());}
		
		appointmentsRegister.get(organizerName).put(UUID, appEvent);
		
		return true; // unknown how to reflect successful report; 
		
	}

	/**
	 * Attendee checks if his event was modified; 
	 */
	@Override
	public AppointmentDTO answer(String organizerName, UUID uid) {
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			
			AppointmentDTO request = (AppointmentDTO) entry.getValue();
			
			if (request.isResponded() == true && request.isComplete() == false) return request; // needs to destroy complete requests; 
			
		}
		
		return null;
	}


	@Override
	public Boolean complete(String organizerName, UUID uid) {

		
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
				
			IAppointmentDTO request = entry.getValue();
			
			if(request.getRequestId().equals(uid)) {
				
				request.setComplete(true);
				
				displayMapByOrganizer(organizerName);
				
				return true; 
				
			}			
		}
		
		return null;
	}








}
