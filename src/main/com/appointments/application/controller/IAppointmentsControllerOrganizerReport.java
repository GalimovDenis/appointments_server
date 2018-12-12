package com.appointments.application.controller;

import org.springframework.http.ResponseEntity;

import com.appointments.application.dto.AppointmentDTO;

/**
 * Services organiser will reach;
 *
 */
public interface IAppointmentsControllerOrganizerReport {

	ResponseEntity<Boolean> report(AppointmentDTO appCreate); // post

}
