package com.appointments.model;

import java.util.UUID;

import com.appointments.application.dto.AppointmentCreate;
import com.appointments.application.dto.AppointmentDelete;
import com.appointments.application.dto.AppointmentRead;
import com.appointments.application.dto.AppointmentUpdate;

public interface IAppointmentsModel {
	
	/**
	 * Attendee posts a request to create an appointment
	 * @param appCreate appointment DTO - AppointmentCreate request; 
	 * @return true if request was added to the queue successfully
	 */
	public Boolean registerCreate(AppointmentCreate appCreate); // post
	public Boolean registerRead(AppointmentRead appRead); 		  // post
	public Boolean registerUpdate(AppointmentUpdate appUpdate); // post
	public Boolean registerDelete(AppointmentDelete appDelete); // post

	/**
	 * Organizer downloads a request to create an appointment
	 * @param organizerName unique name of organizer; 
	 * @return appCreate appointment DTO - AppointmentCreate request; 
	 */
	public AppointmentCreate pendingToCreate(String organizerName);  // get
	public AppointmentRead pendingToRead(String organizerName);	 // get
	public AppointmentUpdate pendingToUpdate(String organizerName);  // get
	public AppointmentDelete pendingToDelete(String organizerName);	 // get
	
	/**
	 * Organizer posts an updated request with appointment creation results
	 * @param appCreate appointment DTO - AppointmentCreate request; 
	 * @return true if updated request was added to the queue successfully
	 */
	public Boolean reportCreate(AppointmentCreate appCreate); //post
	public Boolean reportRead(AppointmentRead appRead);	//post
	public Boolean reportUpdate(AppointmentUpdate appUpdate);	//post
	public Boolean reportDelete(AppointmentDelete appDelete);	//post
	
	/**
	 * Attendee downloads an updated request with appointment creation results
	 * @param organizerName unique name of organizer; 
	 * @param uid Unique ID of a request to create an appointment
	 * @return appCreate appointment DTO - AppointmentCreate request; 
	 */
	public AppointmentCreate answeredCreate(String organizerName, UUID uid); // get 
	public AppointmentRead answeredRead(String organizerName, UUID uid); // get 
	public AppointmentUpdate answeredUpdate(String organizerName, UUID uid); // get 
	public AppointmentDelete answeredDelete(String organizerName, UUID uid); // get 
	

}
