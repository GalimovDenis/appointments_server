package com.appointments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appointments.dto.AppointmentDTO;
import com.appointments.model.IAppointmentsModel;



@RestController  
@RequestMapping(value="/attendee/request")
public class AppointmentsControllerAttendeeRequest implements IAppointmentsControllerAttendeeRequest {


	@Autowired 
	private IAppointmentsModel model;  	//https://stackoverflow.com/a/52526618
	
	@Override
	@PostMapping(produces = "application/json")
    public ResponseEntity<Boolean> register(@RequestBody AppointmentDTO appCreate) {

		System.out.println("INCOMING" + appCreate);
		
		Boolean reg = model.register(appCreate);
		
		HttpStatus status = reg == true ? HttpStatus.ACCEPTED : HttpStatus.NOT_ACCEPTABLE ;
		
		return new ResponseEntity<Boolean>(reg, status);
		
	}
}
