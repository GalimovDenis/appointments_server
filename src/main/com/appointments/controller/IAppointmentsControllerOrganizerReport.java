package com.appointments.controller;

import org.springframework.http.ResponseEntity;

import com.appointments.dto.AppointmentDTO;

/**
 * Services organiser will reach;
 *
 */
public interface IAppointmentsControllerOrganizerReport {

	ResponseEntity<Boolean> report(AppointmentDTO appCreate); // post

}
