package com.appointments.repository;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.appointments.application.entity.RequestKey;
import com.appointments.dto.IAppointmentDTO;

@Repository("AppointmentsRepo")
public class AppointmentsRepo implements IAppointmentsRepo {

	private Map<String, Map<RequestKey, IAppointmentDTO>> appointmentsRegister = new ConcurrentHashMap<String, Map<RequestKey, IAppointmentDTO>>();

	@Override
	public IAppointmentDTO putIfAbsent(String organizerName, IAppointmentDTO appDTO) {

		if (!appointmentsRegister.containsKey(organizerName)) {

			appointmentsRegister.put(organizerName, new TreeMap<RequestKey, IAppointmentDTO>());

		}
		
		System.out.println("Seq+"+appDTO.getSequence());
		System.out.println("Typ+"+appDTO.getRequestType());
		System.out.println("Uid+"+appDTO.getEventId());
		
		//limited to one type request per event?
		RequestKey rk = new RequestKey(appDTO.getSequence(), appDTO.getRequestType(), UUID.fromString(appDTO.getEventId()));
		
		return appointmentsRegister.get(organizerName).put(rk, appDTO);
	}


	@Override
	public Map<RequestKey, IAppointmentDTO> getOrganizerRequests(String organizerName) {

		if (!appointmentsRegister.containsKey(organizerName)) {

			appointmentsRegister.put(organizerName, new TreeMap<RequestKey, IAppointmentDTO>());

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


}
