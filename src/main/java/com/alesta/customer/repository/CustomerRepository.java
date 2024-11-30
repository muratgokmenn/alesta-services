package com.alesta.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alesta.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	boolean existsByPhoneNumber(String phoneNumber);
	
	boolean existsByEmail(String email);
	
	Optional<Customer> findByEmailOrPhoneNumber(String email, String phoneNumber);

}
