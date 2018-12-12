package com.appointments.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.appointments.application.controller","com.appointments.application.model", "com.appointments.application.repository"})
public class AppointmentsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentsServerApplication.class, args);
	}
}
