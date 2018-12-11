package com.appointments.controller;

import org.springframework.http.ResponseEntity;

import com.appointments.dto.AppointmentDTO;

/**
 * Service for appointments attendee; 
 * this are the endpoints attendee will reach when; 
 * 
 *
 */
public interface IAppointmentsControllerAttendeeRequest {

	ResponseEntity<Boolean> register(AppointmentDTO appCreate);  // post
	
}
