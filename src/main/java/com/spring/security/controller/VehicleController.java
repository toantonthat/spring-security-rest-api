package com.spring.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.errors.VehicleNotFoundException;
import com.spring.security.entity.Vehicle;
import com.spring.security.model.VehicleForm;
import com.spring.security.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

//import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

	@Autowired
	private VehicleRepository vehicleRepository;

	@GetMapping("")
	public ResponseEntity<Object> getAll() {
		return ResponseEntity.ok(this.vehicleRepository.findAll());
	}

	@PostMapping("")
	public ResponseEntity<Object> save(@RequestBody VehicleForm form, HttpServletRequest request) {
		Vehicle saved = this.vehicleRepository.save(Vehicle.builder().name(form.getName()).build());
		return ResponseEntity.created(ServletUriComponentsBuilder.fromContextPath(request).path("/v1/vehicles/{id}")
				.buildAndExpand(saved.getId()).toUri()).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Vehicle> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok(this.vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Vehicle> update(@PathVariable("id") Long id, @RequestBody VehicleForm form) {
		Vehicle existed = this.vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException());
		existed.setName(form.getName());
		this.vehicleRepository.save(existed);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		Vehicle existed = this.vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException());
		this.vehicleRepository.delete(existed);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
