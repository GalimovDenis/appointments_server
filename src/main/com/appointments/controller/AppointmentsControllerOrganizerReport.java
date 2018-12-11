package com.appointments.controller;

import com.appointments.dto.AppointmentDTO;
import com.appointments.model.IAppointmentsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/organizer/report")
public class AppointmentsControllerOrganizerReport implements IAppointmentsControllerOrganizerReport {

	@Autowired
	private IAppointmentsModel model; // https://stackoverflow.com/a/52526618

	@Override
	@PostMapping(produces = "application/json")
	public ResponseEntity<Boolean> report(@RequestBody AppointmentDTO appCreate) {

		Boolean reg = model.report(appCreate);

		HttpStatus status = reg == true ? HttpStatus.ACCEPTED : HttpStatus.ALREADY_REPORTED;

		return new ResponseEntity<Boolean>(reg, status);
	}

}
