package com.appointments.controller;

import com.appointments.application.dto.AppointmentDTO;
import com.appointments.model.IAppointmentsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController  
@RequestMapping(value="/attendee/request")
public class AppointmentsControllerAttendeeRequest implements IAppointmentsControllerAttendeeRequest {


	@Autowired 
	private IAppointmentsModel model;  	//https://stackoverflow.com/a/52526618
	
	@Override
	@PostMapping(value="/create", produces = "application/json")
    public Boolean registerCreate(@RequestBody AppointmentDTO appCreate) {

        return model.register(appCreate);
	
	}

	@Override
	@PostMapping(value="/read", produces = "application/json")
    public Boolean registerRead(@RequestBody AppointmentDTO appRead) {

        return model.register(appRead);
		
	}

	@Override
	@PostMapping(value="/update", produces = "application/json")
    public Boolean registerUpdate(@RequestBody AppointmentDTO appUpdate) {

        return model.register(appUpdate);
	}

	@Override
	@PostMapping(value="/delete", produces = "application/json")
    public Boolean registerDelete(@RequestBody AppointmentDTO appDelete) {

        return model.register(appDelete);
	}

}
