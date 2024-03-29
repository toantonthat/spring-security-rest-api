package com.spring.security.entity;

import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractAuditingEntity<Long> implements UserDetails {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Column(name = "user_name")
	private String username;

	@NotEmpty
	@Column(name = "password")
	private String password;

//	@ElementCollection(fetch = FetchType.EAGER)
//	@Builder.Default
//	private List<String> roles = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(role -> role.getName().toString()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
