package com.appointments.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appointments.application.entity.RequestKey;
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
		
		return appRepo.putIfAbsent(organizerName, appRequest);
		
	}

	@Override
	public AppointmentDTO pendingTo(String organizerName, RequestType type) {
			
		Map<RequestKey, IAppointmentDTO> mapOfPendingCreations = appRepo.getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<RequestKey, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			
			if(!entry.getKey().getType().equals(type)) continue;
			
			AppointmentDTO request = (AppointmentDTO) entry.getValue();
			
			if (request.isResponded() == false) return request;
		}
		
		return null; 
	
	}


	public Boolean report(IAppointmentDTO appRequest) { // need generic dto or split reports; 
		
		String organizerName = appRequest.getOrganizer();
		
		return appRepo.putIfAbsent(organizerName, appRequest);
		
		// unknown how to reflect successful report; 
		
	}

	///can send in a read request when you need an update request;
	@Override
	public AppointmentDTO answer(String organizerName, UUID eventID, int sequence) {
		
		Map<RequestKey, IAppointmentDTO> mapOfPendingCreations = appRepo.getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<RequestKey, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			
			if(!entry.getKey().getEventID().equals(eventID)) continue;
			
			if(entry.getKey().getSequence()!=sequence) continue;

			AppointmentDTO request = (AppointmentDTO) entry.getValue();
			
			if (request.isResponded() == true && request.isComplete() == false) return request; // needs to destroy complete requests; 
			
		}
		
		return null;
	}

	// two update requests? can't use RequestUID bc each must be idempotent; can't use Key+Event bc they'll be the same; 
	@Override
	public Boolean complete(String organizerName, UUID eventID, int sequence) {

		Map<RequestKey, IAppointmentDTO> mapOfPendingCreations = appRepo.getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<RequestKey, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {

			if(!entry.getKey().getEventID().equals(eventID)) continue;
			
			if(entry.getKey().getSequence()!=sequence) continue;
	
			IAppointmentDTO request = entry.getValue();
					
			request.setComplete(true);
				
			return true; 
						
		}
		
		return null;
	}


	@Override
	public void clear() {
		appRepo.clear();
	}








}
