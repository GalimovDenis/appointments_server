package com.appointments.application.controller;

import com.appointments.application.dto.AppointmentDTO;
import com.appointments.application.dto.RequestType;
import com.appointments.application.model.IAppointmentsModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController  
@RequestMapping(value="/organizer/request")
public class AppointmentsControllerOrganizerRequest implements IAppointmentsControllerOrganizerRequest {

	@Autowired 
	private IAppointmentsModel model;  //https://stackoverflow.com/a/52526618
	
	@Override
	@GetMapping(value="/create/", produces = "application/json")
    public AppointmentDTO IncomingNewEvent(@RequestParam(value = "orgname") String organizerName) {

        return model.pendingTo(organizerName, RequestType.CREATE);
		
	}

	@Override
	@GetMapping(value="/read", produces = "application/json")
    public AppointmentDTO IncomingReport(@RequestParam(value = "orgname") String organizerName) {
		
		// TODO Auto-generated method stub
        return model.pendingTo(organizerName, RequestType.READ);
	
	}

	@Override
	@GetMapping(value="/update", produces = "application/json")
    public AppointmentDTO IncomingChangeEvent(@RequestParam(value = "orgname") String organizerName) {
	
        return model.pendingTo(organizerName,RequestType.UPDATE);
	}

	@Override
	@GetMapping(value="/delete", produces = "application/json")
    public AppointmentDTO IncomingDeleteEvent(@RequestParam(value = "orgname") String organizerName) {
		// TODO Auto-generated method stub
        return model.pendingTo(organizerName, RequestType.DELETE);
	}

}
