package com.appointments.model;

import java.util.UUID;

import com.appointments.application.dto.AppointmentDTO;
import com.appointments.application.dto.IAppointmentDTO;
import com.appointments.application.dto.RequestType;

public interface IAppointmentsModel {
	
	/**
	 * Attendee posts a request to create an appointment
	 * @param appCreate appointment DTO - AppointmentDTO request; 
	 * @return true if request was added to the queue successfully
	 */
	public Boolean register(IAppointmentDTO appointment); // post

	/**
	 * Organizer downloads a request to create an appointment
	 * @param organizerName unique name of organizer; 
	 * @return AppointmentDTO request; 
	 */
	public AppointmentDTO pendingTo(String organizerName, RequestType type);  // get
	
	/**
	 * Organizer posts an updated request with appointment creation results
	 * @param appCreate appointment DTO - AppointmentDTO request; 
	 * @return true if updated request was added to the queue successfully
	 */
	public Boolean report(IAppointmentDTO appointment); //post
	
	/**
	 * Attendee downloads an updated request with appointment creation results
	 * @param organizerName unique name of organizer; 
	 * @param uid Unique ID of a request to create an appointment
	 * @return AppointmentDTO request; 
	 */
	public AppointmentDTO answer(String organizerName, UUID uid); // get
	
	
	/**
	 * Attendee reports that an event is complete;
	 * organizerName unique name of organizer; 
	 * @param uid Unique ID of a request to create an appointment
	 * @return
	 */
	public Boolean complete(String organizerName, UUID uid); //post
	

    


    


    

}
