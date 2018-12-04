package com.appointments.repository;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.appointments.dto.IAppointmentDTO;

@Repository("AppointmentsRepo")
public class AppointmentsRepo implements IAppointmentsRepo {

	private Map<String, Map<UUID, IAppointmentDTO>> appointmentsRegister = new ConcurrentHashMap<String, Map<UUID, IAppointmentDTO>>();

	@Override
	public IAppointmentDTO putIfAbsent(String organizerName, IAppointmentDTO appDTO) {

		if (!appointmentsRegister.containsKey(organizerName)) {

			appointmentsRegister.put(organizerName, new TreeMap<UUID, IAppointmentDTO>());

		}
		// Consider defensive copying: new AppointmentDTO(appDTO));
		return appointmentsRegister.get(organizerName).put(appDTO.getRequestId(), appDTO);
	}


	@Override
	public Map<UUID, IAppointmentDTO> getOrganizerRequests(String organizerName) {

		if (!appointmentsRegister.containsKey(organizerName)) {

			appointmentsRegister.put(organizerName, new TreeMap<UUID, IAppointmentDTO>());

		}

		Map<UUID, IAppointmentDTO> mapOfPendingCreations = appointmentsRegister.get(organizerName);
		
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

		Map<UUID, IAppointmentDTO> organizerRequests = appointmentsRegister.get(organizerName);
		
		for (IAppointmentDTO appDTO : organizerRequests.values()) {
			
			if (appDTO.isComplete()) {
				
				organizerRequests.remove(appDTO.getRequestId());
				
			}
			
		}

	}
	
	/**
	 * Debug display
	 * @param organizerName
	 */
	@SuppressWarnings("unused")
	private void displayMapByOrganizer(String organizerName) {
		
		for(Entry<UUID, IAppointmentDTO> entry : this.getOrganizerRequests(organizerName).entrySet()){
			System.out.println("UUID "+entry.getKey()+" entry "+entry.getValue().toString());
		}
		
	}
	
	@SuppressWarnings("unused")
	private void displayMap() {
		//TODO:full map display
	}


}
