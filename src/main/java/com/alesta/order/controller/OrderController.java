package com.alesta.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alesta.order.exception.OrderException;
import com.alesta.order.model.Order;
import com.alesta.order.service.OrderService;
import com.alesta.response.ApiResponse;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/register")
	public ApiResponse<Order> saveOrder(@RequestBody Order order) {
		try {
			return orderService.saveOrder(order);
		} catch (OrderException e) {
			return new ApiResponse<>(false, "Order registered failed : " + e.getMessage(), order);
		}
	}
	
	@GetMapping("/{customerId}")
	List<Order> getOrdersByCustomerId(@PathVariable Long customerId) throws OrderException{
		return orderService.getOrdersByCustomerId(customerId);
	}

}
