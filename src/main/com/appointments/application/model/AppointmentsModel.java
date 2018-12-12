package com.appointments.application.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appointments.application.dto.AppointmentDTO;
import com.appointments.application.dto.IAppointmentDTO;
import com.appointments.application.dto.RequestType;
import com.appointments.application.entity.IRequestKey;
import com.appointments.application.repository.IAppointmentsRepo;


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
			
		Map<IRequestKey, IAppointmentDTO> mapOfPendingCreations = appRepo.getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<IRequestKey, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			
			if(!entry.getKey().getRequestType().equals(type)) continue;
			
			AppointmentDTO request = (AppointmentDTO) entry.getValue();

			if (request.isComplete()) continue;
			
			if (request.isResponded() == false) return request;
		}
		
		return null; 
	
	}


	public Boolean report(IAppointmentDTO appRequest) { // need generic dto or split reports; 
		
		String organizerName = appRequest.getOrganizer();
		
		return appRepo.putIfAbsent(organizerName, appRequest); 
		
	}

	///can send in a read request when you need an update request;
	@Override
	public AppointmentDTO answer(String organizerName, UUID eventID, int sequence) {
		
		Map<IRequestKey, IAppointmentDTO> mapOfPendingCreations = appRepo.getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<IRequestKey, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {
			
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

		Map<IRequestKey, IAppointmentDTO> mapOfPendingCreations = appRepo.getOrganizerRequests(organizerName);

		if (mapOfPendingCreations.size() == 0) return null; 
		
		for (Entry<IRequestKey, IAppointmentDTO> entry : mapOfPendingCreations.entrySet()) {

			if(!entry.getKey().getEventID().equals(eventID)) continue;
			
			if(entry.getKey().getSequence()!=sequence) continue;
	
			IAppointmentDTO request = entry.getValue();
			
			//System.out.println("Request to Complete" + request);
					
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
