package com.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.security.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}
