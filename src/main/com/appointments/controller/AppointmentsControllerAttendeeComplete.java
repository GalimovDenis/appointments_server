package com.appointments.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appointments.model.IAppointmentsModel;



@RestController  
@RequestMapping(value="/attendee/complete")
public class AppointmentsControllerAttendeeComplete implements IAppointmentsControllerAttendeeComplete {


	@Autowired 
	private IAppointmentsModel model;  	
	
	@Override
	@PostMapping(produces = "application/json")
    public Boolean eventComplete(@RequestParam(value = "orgname") String organizerName,
    		@RequestParam(value = "uid") UUID uid,
    		@RequestParam(value = "sequence") int sequence
    		) {

        return model.complete(organizerName, uid, sequence);
	
	}




}
