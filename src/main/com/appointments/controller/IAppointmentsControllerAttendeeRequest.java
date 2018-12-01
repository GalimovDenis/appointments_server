package com.appointments.controller;

import com.appointments.application.dto.AppointmentDTO;

/**
 * Service for appointments attendee; 
 * this are the endpoints attendee will reach when; 
 * 
 *
 */
public interface IAppointmentsControllerAttendeeRequest {

    Boolean registerCreate(AppointmentDTO appCreate);  // post

    Boolean registerRead(AppointmentDTO appRead); // post

    Boolean registerUpdate(AppointmentDTO appUpdate); // post

    Boolean registerDelete(AppointmentDTO appDelete);    // post
	
}
