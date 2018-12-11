package com.appointments.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appointments.dto.AppointmentDTO;
import com.appointments.model.IAppointmentsModel;



@RestController  
@RequestMapping(value="/attendee/report")
public class AppointmentsControllerAttendeeReport implements IAppointmentsControllerAttendeeReport {


	@Autowired 
	private IAppointmentsModel model;  	//https://stackoverflow.com/a/52526618
	
	@Override
	@GetMapping(produces = "application/json")
    public AppointmentDTO report(@RequestParam(value = "orgname") String organizerName, 
    		@RequestParam(value = "uid") UUID uid,
    		@RequestParam(value = "sequence") int sequence
    		) {
	
        return model.answer(organizerName, uid, sequence);
		
	}


}
