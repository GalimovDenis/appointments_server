package com.appointments.controller;

import com.appointments.application.dto.AppointmentDTO;

/**
 * Services organiser will reach;
 *
 */
public interface IAppointmentsControllerOrganizerReport {

    Boolean reportCreate(AppointmentDTO appCreate); // post

    Boolean reportRead(AppointmentDTO appRead); // post

    Boolean reportUpdate(AppointmentDTO appUpdate); // post

    Boolean reportDelete(AppointmentDTO appDelete); // post


}
