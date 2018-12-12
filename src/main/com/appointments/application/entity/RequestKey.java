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
public final class RequestKey implements Comparable<RequestKey> {

	final int sequence;
	final RequestType type;
	final UUID eventID;
	@Override
	
	public int compareTo(RequestKey other) {
		// TODO Auto-generated method stub
		if(this.sequence != other.sequence) {
			return this.sequence - other.sequence;
		}
		if(!this.type.equals(other.type)) {
			return this.type.compareTo(other.type);
		}
		if(!this.eventID.equals(other.eventID)) {
			return this.eventID.compareTo(other.eventID);
		}
		return 0;
	}

	

}
