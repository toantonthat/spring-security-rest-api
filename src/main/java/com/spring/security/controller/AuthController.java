package com.spring.security.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.spring.security.entity.Role;
import com.spring.security.entity.User;
import com.spring.security.enums.ERole;
import com.spring.security.model.MessageResponse;
import com.spring.security.model.SignupRequest;
import com.spring.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.jwt.JwtTokenProvider;
import com.spring.security.model.AuthenticationRequest;
import com.spring.security.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder encoder;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUserName())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
		// Create new user's account
		User user = User.builder()
				.username(signUpRequest.getUserName())
				.password(encoder.encode(signUpRequest.getPassWord()))
				.build();

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "ROLE_ADMIN":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
						break;
					case "ROLE_MODERATOR":
						Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);
						break;
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/signin")
	public ResponseEntity<Object> signin(@RequestBody @Valid AuthenticationRequest req) {
		try {
			String username = req.getUserName();
			
			System.out.println("authenticationManager " + authenticationManager);
			System.out.println("username " + username);
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, req.getPassWord()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"))
					.getRoles().stream().map(r -> r.getName().toString()).collect(Collectors.toList()));

			List<String> roles = authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("roles", roles);
			model.put("token", token);
			return ResponseEntity.ok(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}
}
