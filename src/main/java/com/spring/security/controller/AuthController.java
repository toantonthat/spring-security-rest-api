package com.spring.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	private UserRepository users;

	@PostMapping("/signin")
	public ResponseEntity<Object> signin(@RequestBody @Valid AuthenticationRequest req) {
		try {
			String username = req.getUserName();
			
			System.out.println("authenticationManager " + authenticationManager);
			System.out.println("username " + username);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, req.getPassWord()));
			String token = jwtTokenProvider.createToken(username, this.users.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles());
			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			return ResponseEntity.ok(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}
}
