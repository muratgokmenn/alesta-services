package com.alesta.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alesta.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
	List<Order> findAllById(Long customerId);

}
