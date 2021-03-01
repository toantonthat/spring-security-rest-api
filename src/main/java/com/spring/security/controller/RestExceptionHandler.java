package com.spring.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.spring.errors.VehicleNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

	public ResponseEntity<Object> vehicleNotFound(VehicleNotFoundException exeption, WebRequest request) {
		log.debug("handling VehicleNotFoundException...");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
