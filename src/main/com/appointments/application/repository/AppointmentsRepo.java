package com.appointments.application.repository;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.appointments.application.dto.IAppointmentDTO;
import com.appointments.application.entity.RequestKey;

@Repository("AppointmentsRepo")
public class AppointmentsRepo implements IAppointmentsRepo {

	private Map<String, Map<RequestKey, IAppointmentDTO>> appointmentsRegister = new ConcurrentHashMap<String, Map<RequestKey, IAppointmentDTO>>();

	@Override
	public boolean putIfAbsent(String organizerName, IAppointmentDTO appDTO) {

		if (!appointmentsRegister.containsKey(organizerName)) {

			appointmentsRegister.put(organizerName, new ConcurrentHashMap<RequestKey, IAppointmentDTO>());

		}
		
		//limited to one type request per event?
		RequestKey rk = new RequestKey(appDTO.getSequence(), appDTO.getRequestType(), UUID.fromString(appDTO.getEventId()));
	
		IAppointmentDTO previousDTO = appointmentsRegister.get(organizerName).get(rk);
		
		if(previousDTO == null) {
			appointmentsRegister.get(organizerName).put(rk, appDTO);
			return true;
		}
		
		if(previousDTO.equals(appDTO)) {
			return false;
		}
		
		appointmentsRegister.get(organizerName).put(rk, appDTO);
		return true;
		
	}


	@Override
	public Map<RequestKey, IAppointmentDTO> getOrganizerRequests(String organizerName) {

		if (!appointmentsRegister.containsKey(organizerName)) {

			appointmentsRegister.put(organizerName, new ConcurrentHashMap<RequestKey, IAppointmentDTO>());

		}

		Map<RequestKey, IAppointmentDTO> mapOfPendingCreations = appointmentsRegister.get(organizerName);
		
		//no defensive copying; cliens can mutate mapOfPendingCreations
		return mapOfPendingCreations;

	}

	@Override
	public void deleteCompleteRequests() {

		for (String organizerName : appointmentsRegister.keySet()) {
			deleteCompleteRequests(organizerName);
		}

	}

	@Override
	public void deleteCompleteRequests(String organizerName) {

		Map<RequestKey, IAppointmentDTO> organizerRequests = appointmentsRegister.get(organizerName);
		
		for (Entry<RequestKey, IAppointmentDTO> entry: organizerRequests.entrySet()) {
			
			if (entry.getValue().isComplete()) {
				
				organizerRequests.remove(entry.getKey());
				
			}
			
		}

	}
	
	/**
	 * Debug display
	 * @param organizerName
	 */
	@SuppressWarnings("unused")
	private void displayMapByOrganizer(String organizerName) {
		
		for(Entry<RequestKey, IAppointmentDTO> entry : this.getOrganizerRequests(organizerName).entrySet()){
			System.out.println("RequestKey "+entry.getKey()+" entry "+entry.getValue().toString());
		}
		
	}
	
	@SuppressWarnings("unused")
	private void displayMap() {
		//TODO:full map display
	}


	@Override
	public void clear() {
		appointmentsRegister = new ConcurrentHashMap<String, Map<RequestKey, IAppointmentDTO>>();
	}


}
