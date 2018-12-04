package com.appointments.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.appointments.controller","com.appointments.model", "com.appointments.repository"})
public class AppointmentsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentsServerApplication.class, args);
	}
}
