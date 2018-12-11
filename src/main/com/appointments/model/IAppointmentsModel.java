package com.appointments.model;

import java.util.UUID;

import com.appointments.dto.AppointmentDTO;
import com.appointments.dto.IAppointmentDTO;
import com.appointments.dto.RequestType;

public interface IAppointmentsModel {
	
	/**
	 * Attendee posts a request to create an appointment
	 * @param appCreate appointment DTO - AppointmentDTO request; 
	 * @return true if request was added to the queue successfully
	 */
	public Boolean register(IAppointmentDTO appointment); // post
	//TODO: return bad code if this AppointmentID is already in the system

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
	 * @param eventID Unique ID of appointment event
	 * @return AppointmentDTO request;
	 * @param sequence number of request revision
	 
	 */
	public AppointmentDTO answer(String organizerName, UUID eventID, int sequence); // get
	// TODO: change to the Appointment UID from request ID 
	
	/**
	 Attendee reports that an event is complete;
	 * organizerName unique name of organizer; 
	 * @param organizerName 
	 * @param eventID Unique ID of appointment event
	 * @param sequence number of request revision
	 * @return
	 */
	public Boolean complete(String organizerName, UUID eventID, int sequence); //post
	
    
	public void clear();

    


    

}
