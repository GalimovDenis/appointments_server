package com.appointments.application.entity;

import com.appointments.application.dto.IAppointmentDTO;

public enum RequestStatus {
	New, Registered, Responded, Complete;
	
	public static RequestStatus create(IAppointmentDTO dto) {
		if(!dto.isRegistered()) return RequestStatus.New;
		if(!dto.isResponded()) return RequestStatus.Registered;
		if(!dto.isComplete()) return RequestStatus.Responded;
		return RequestStatus.Complete;
	}
}


