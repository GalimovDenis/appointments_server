package com.appointments.application.controller;

import org.springframework.http.ResponseEntity;

import com.appointments.application.dto.AppointmentDTO;

/**
 * Service for appointments attendee; 
 * this are the endpoints attendee will reach when; 
 * 
 *
 */
public interface IAppointmentsControllerAttendeeRequest {

	ResponseEntity<Boolean> register(AppointmentDTO appCreate);  // post
	
}
