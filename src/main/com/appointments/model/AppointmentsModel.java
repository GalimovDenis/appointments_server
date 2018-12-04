package com.appointments.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appointments.dto.AppointmentDTO;
import com.appointments.dto.IAppointmentDTO;
import com.appointments.dto.RequestType;
import com.appointments.repository.IAppointmentsRepo;


/**
 * Mediator between organizer and attendee; 
 */
@Component("AppointmentsModel")
public class AppointmentsModel implements IAppointmentsModel {

	@Autowired
	IAppointmentsRepo appRepo;
	
	public AppointmentsModel() {
		super();
	}
	

	public Boolean register(IAppointmentDTO appRequest	) {
		
		String organizerName = appRequest.getOrganizer();
		
		appRequest.setRegistered(true);
		
		appRepo.putIfAbsent(organizerName, appRequest);
		
		return true;
	}

	@Override
	public AppointmentDTO pendingTo(String organizerName, RequestType type) {
			
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = appRepo.getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			AppointmentDTO request = (AppointmentDTO) entry.getValue();
			if (!request.getRequestType().equals(type)) continue; 
			if (request.isResponded() == false) return request;
		}
		
		return null; 
	
	}


	public Boolean report(IAppointmentDTO appRequest) { // need generic dto or split reports; 
		
		String organizerName = appRequest.getOrganizer();
		
		appRepo.putIfAbsent(organizerName, appRequest);
		
		return true; // unknown how to reflect successful report; 
		
	}

	@Override
	public AppointmentDTO answer(String organizerName, UUID uid) {
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = appRepo.getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			
			AppointmentDTO request = (AppointmentDTO) entry.getValue();
			
			if (request.isResponded() == true && request.isComplete() == false) return request; // needs to destroy complete requests; 
			
		}
		
		return null;
	}


	@Override
	public Boolean complete(String organizerName, UUID uid) {

		
		
		Map<UUID, IAppointmentDTO> mapOfPendingCreations = appRepo.getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<UUID, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
				
			IAppointmentDTO request = entry.getValue();
			
			if(request.getRequestId().equals(uid)) {
				
				request.setComplete(true);
				
				return true; 
				
			}			
		}
		
		return null;
	}








}
