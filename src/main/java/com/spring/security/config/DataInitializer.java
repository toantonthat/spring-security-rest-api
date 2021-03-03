package com.spring.security.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.spring.security.entity.Brand;
import com.spring.security.entity.User;
import com.spring.security.entity.Vehicle;
import com.spring.security.repository.BrandRepository;
import com.spring.security.repository.UserRepository;
import com.spring.security.repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Qualifier("dataInit")
@Slf4j
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static long brandId = 0L;
	
	@Override
	public void run(String... args) throws Exception {
		log.debug("initializing vehicles data...");

		if (this.brandRepository.count() == 0) {
			Arrays.asList("Toyota", "BMW", "Vinfast", "Tesla", "Honda", "Saburu", "Huyndai", "KIA", "Audi")
					.forEach(v -> {
						Brand brand = Brand.builder().name(v).build();
						brand.setCreatedBy("toantt");
						brand.setCreatedDate(Instant.now());
						brand.setDeleted(false);
						brand.setVersion(1L);
						this.brandRepository.saveAndFlush(brand);
					});
		}
		if (this.vehicleRepository.count() == 0) {
			Arrays.asList("moto", "car", "truck", "bike").forEach(v -> {
				brandId++;
				Vehicle vehicle = Vehicle.builder().name(v).build();
				vehicle.setCreatedBy("toantt");
				vehicle.setCreatedDate(Instant.now());
				vehicle.setDeleted(false);
				vehicle.setVersion(1L);
				vehicle.setBrand(this.brandRepository.findById(brandId).get());
				this.vehicleRepository.saveAndFlush(vehicle);
			});
		}
		
		if (userRepository.count() == 0) {
			User user = User.builder()
					.username("toantt")
					.password(this.passwordEncoder.encode("123456"))
					.roles(Arrays.asList("ROLE_USER"))
					.build();
			user.setCreatedBy("toantt");
			user.setCreatedDate(Instant.now());
			user.setDeleted(false);
			user.setVersion(1L);
			
			User admin = User.builder()
					.username("admin")
					.password(this.passwordEncoder.encode("123456"))
					.roles(Arrays.asList("ROLE_ADMIN"))
					.build();
			admin.setCreatedBy("toantt");
			admin.setCreatedDate(Instant.now());
			admin.setDeleted(false);
			admin.setVersion(1L);
			
			this.userRepository.saveAndFlush(user);
			this.userRepository.saveAndFlush(admin);
		}
		
		log.debug("printing all vehicles...");
		this.vehicleRepository.findAll().forEach(v -> log.debug(" Vehicle :" + v.toString()));
	}
}
