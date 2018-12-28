package com.appointments.application.entity;

import java.util.UUID;

import com.appointments.application.dto.RequestType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public final class RequestKey implements IRequestKey {

	@EqualsAndHashCode.Exclude final RequestStatus status;
	final int sequence;
	final RequestType requestType;
	final UUID eventID;
	
	@Override
	public int compareTo(IRequestKey other) {
		if(!this.status.equals(other.getStatus())) {
			return this.status.ordinal() - other.getStatus().ordinal();
		}
		if(this.sequence != other.getSequence()) {
			return this.sequence - other.getSequence();
		}
		if(!this.requestType.equals(other.getRequestType())) {
			return this.requestType.compareTo(other.getRequestType());
		}
		if(!this.eventID.equals(other.getEventID())) {
			return this.eventID.compareTo(other.getEventID());
		}
		return 0;
	}

	

}
