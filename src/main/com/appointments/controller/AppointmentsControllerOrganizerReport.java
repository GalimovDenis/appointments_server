package com.appointments.controller;

import com.appointments.application.dto.AppointmentDTO;
import com.appointments.model.IAppointmentsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController  
@RequestMapping(value="/organizer/report")
public class AppointmentsControllerOrganizerReport implements IAppointmentsControllerOrganizerReport {

	@Autowired 
	private IAppointmentsModel model;  //https://stackoverflow.com/a/52526618

	@Override
	@PostMapping(value="/create", produces = "application/json")
    public Boolean reportCreate(@RequestBody AppointmentDTO appCreate) {
		// TODO Auto-generated method stub
        return model.report(appCreate);
	}

	@Override
	@PostMapping(value="/read", produces = "application/json")
    public Boolean reportRead(@RequestBody AppointmentDTO appRead) {
		// TODO Auto-generated method stub
        return model.report(appRead);
	}

	@Override
	@PostMapping(value="/update", produces = "application/json")
    public Boolean reportUpdate(@RequestBody AppointmentDTO appUpdate) {
		// TODO Auto-generated method stub
        return model.report(appUpdate);
	}

	@Override
	@PostMapping(value="/delete", produces = "application/json")
    public Boolean reportDelete(@RequestBody AppointmentDTO appDelete) {
		// TODO Auto-generated method stub
        return model.report(appDelete);
	}

}
