package com.spring.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value =  HttpStatus.NOT_FOUND)
public class VehicleNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public VehicleNotFoundException() {
	}

	public VehicleNotFoundException(Long vehicleId) {
		super("Vehicle: " + vehicleId + " not found.");
	}
}
