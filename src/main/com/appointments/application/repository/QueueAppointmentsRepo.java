package com.appointments.application.repository;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.appointments.application.dto.IAppointmentDTO;
import com.appointments.application.dto.RequestType;
import com.appointments.application.entity.IRequestKey;
import com.appointments.application.entity.RequestStatus;

@Repository("AppointmentsRepo")
public class QueueAppointmentsRepo implements IAppointmentsRepo {

	//TODO: real sql repo with getRequest, please; 

	private Map<String, Map<IRequestKey, IAppointmentDTO>> appointmentsRegister = new ConcurrentHashMap<String, Map<IRequestKey, IAppointmentDTO>>();

	private void addOrganizerIfEmpty(String organizerName) {
		if (!appointmentsRegister.containsKey(organizerName)) {
			appointmentsRegister.put(organizerName, new ConcurrentHashMap<IRequestKey, IAppointmentDTO>());
		}
	}
	
	@Override
	public boolean putIfAbsent(String organizerName, IAppointmentDTO appDTO) {

		addOrganizerIfEmpty(organizerName);

		IRequestKey rk = IRequestKey.create(appDTO);
		
		IAppointmentDTO previousDTO = appointmentsRegister.get(organizerName).get(rk);

		if (previousDTO == null) {
			appointmentsRegister.get(organizerName).put(rk, appDTO);
			return true;
		}

		if (previousDTO.equals(appDTO)) {
			return false;
		}

		appointmentsRegister.get(organizerName).put(rk, appDTO);
		return true;

	}

	@Override
	public Map<IRequestKey, IAppointmentDTO> getOrganizerRequests(String organizerName) {

		addOrganizerIfEmpty(organizerName);

		Map<IRequestKey, IAppointmentDTO> mapOfPendingCreations = appointmentsRegister.get(organizerName);

		// no defensive copying; cliens can mutate mapOfPendingCreations
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

		Map<IRequestKey, IAppointmentDTO> organizerRequests = appointmentsRegister.get(organizerName);

		for (Entry<IRequestKey, IAppointmentDTO> entry : organizerRequests.entrySet()) {

			if (entry.getValue().isComplete()) {

				organizerRequests.remove(entry.getKey());

			}

		}

	}

	@Override
	public void clear() {
		appointmentsRegister = new ConcurrentHashMap<String, Map<IRequestKey, IAppointmentDTO>>();
	}

	@Override
	public IAppointmentDTO getRequest(String orgName, RequestType requestType, RequestStatus requestStatus,
			UUID eventID, int sequence) {
		// TODO Auto-generated method stub
		return null;
	}

}
