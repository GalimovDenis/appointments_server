package com.appointments.application.dto;

public class EventUUID {
	long UUID;

	public EventUUID(long uUID) {
		super();
		UUID = uUID;
	}
	
	public EventUUID() {
		super();
	}
	
	public long getUUID() {
		return UUID;
	}

	public void setUUID(long uUID) {
		UUID = uUID;
	}


	

}
