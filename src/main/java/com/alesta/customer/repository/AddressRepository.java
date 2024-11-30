package com.alesta.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alesta.customer.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
	
	List<Address> findAllByCustomerId(Long customerId);

}
