package com.spring.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

	@JsonProperty("username")
	private String userName;

	@JsonProperty("password")
	private String passWord;

	private Set<String> roles;
}
