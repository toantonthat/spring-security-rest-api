package com.spring.security.repository;

import java.util.Optional;

import com.spring.security.entity.Role;
import com.spring.security.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
